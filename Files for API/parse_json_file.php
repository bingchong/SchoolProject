<?
error_reporting(E_ERROR);
//error_reporting(E_STRICT);
include "/var/www/vhosts/mdotm.com/include/storage.php";
include "/var/www/vhosts/mdotm.com/httpdocs/system/application/helpers/uifunc_helper.php";
include "/home/bing/generate_error_code.php";
//For testing
//include "/var/www/vhosts/mdotm.com/analytics/bing/baidu/www00_generate_error_code.php";

//Allows sql queries
$storage = new Storage;
$database = $storage->getMdotmDatabase();

//Debug mode or not
$debug = 0;
$date = str_replace(' ', '-', date('Ymd H_i'));
//Array for storing the list of rows affected
$updated = array();

//Old url before the test
//$md5url = "http://bs.baidu.com/app-ads/foreign/text/mdotm.txt.md5";

//Actual url
//$md5url = "http://bs.baidu.com/app-ads/foreign-online/text/mdotm.txt.md5";
// -- COMMENTED OUT BY RODNEY 6/12/2014  $md5url = "http://bs.baidu.com/globalads/foreign-online/text/mdotm_20140529_middle.json.md5";
$md5url = "http://bs.baidu.com/globalads/foreign-online/text/mdotm_middle.json.md5"; //Added by Rodney - 6/12/2014

//Test url
//$md5url = "http://bs.baidu.com/globalads/foreign-online/text/mdotm_20140520_middle.json.md5";

//echo "APIURL: $apiurl \n";                                                      
$md5session = curl_init($md5url);
curl_setopt($md5session, CURLOPT_RETURNTRANSFER, true);
$md5res = trim(curl_exec($md5session));
$md5code = curl_getinfo($md5session, CURLINFO_HTTP_CODE);
$md5response = $md5res;
curl_close($md5session);

$tmp = explode(' ',$md5response);
$md5 = $tmp[0];

//Old url before the test
//$apiurl = "http://bs.baidu.com/app-ads/foreign/text/mdotm.txt";
//Actual url
//$apiurl = "http://bs.baidu.com/app-ads/foreign-online/text/mdotm.txt";
// -- COMMENTED OUT BY RODNEY 6/12/2014 $apiurl = "http://bs.baidu.com/globalads/foreign-online/text/mdotm_20140529_middle.json";
$apiurl = "http://bs.baidu.com/globalads/foreign-online/text/mdotm_middle.json"; //Added by Rodney - 6/12/2014

//Test url
//$apiurl = "http://bs.baidu.com/globalads/foreign-online/text/mdotm_20140520_middle.json";

//Testing
//$apiurl = "http://www1001.mdotm.com/ads/baidu_testinput.txt";

$all_errors = array();
$sql_errors = array();
$warnings = array();

echo "APIURL: $apiurl \n"; 
$session = curl_init($apiurl);
curl_setopt($session, CURLOPT_RETURNTRANSFER, true);
$res = trim(curl_exec($session));
$code = curl_getinfo($session, CURLINFO_HTTP_CODE);
$response = json_decode($res, true);
//Check if it is a valid json object
if( json_last_error()!=JSON_ERROR_NONE){
  $all_errors['json'][]="JSON object is not valid";
}
curl_close($session);

$baidu_userIDs = array();
$users = $response['users'];
foreach ($users as $i => $user){
  //Check if there's any error. If there is error, it will not continue with the creation/update of the levels below it. 
  $error=check_user_values($user);
  if(!empty($error['error_codes'])){
    $all_errors['users'][] = $error;
    continue;
  }

  $external_accountID = $user['id'];
  $baidu_userIDs[] = $external_accountID;
  $accountName = $user['userName'];
  $budget = $user['totalBudget'];
  $status = $user['status'];
  $domain = $user['domain'];
  //baidu accountID = 10297
  $agencyAccountID = 10297;

  //get ad category from domain
  $tmp = explode('.',$domain);
  $adcategories = $tmp[0];

  //SQL for account
  //Check if we should use update or insert, assuming external accountID is unique 
  $check_account = "select * from account where agencyAccountID=10297 and external_accountID=$external_accountID";
  echo "check account: $check_account \n";
  if ( $res = mysql_query($check_account) ) {
    //If account already existed
    if ( $a = mysql_fetch_object($res) ) {
      $existing_accountID = $a->accountID;
      $sql0 = "update account set lastUpdatedBy=Now(), status='$status', agencyAccountID=10297, external_accountID=$external_accountID, company='$accountName', domain='$domain'  where accountID='$existing_accountID' limit 1"; //agencyAccountID=10297 and external_accountID=$external_accountID and accountID='$existing_accountID' limit 1";
    }
    else{
      //Get the parent's credit line and assign the same amount for the child
      $p_sql = "select credit from account where accountID=10297";
      if ( $p = mysql_query($p_sql) ){
	if ( $p2 = mysql_fetch_object($res) ){
	  $p_credit = $p2->credit;
	}
      }

      $sql0 = "insert into account (lastUpdatedBy, status, agencyAccountID, external_accountID, company, domain, credit, accountManager) values (Now(), '$status', 10297, $external_accountID, '$accountName', '$domain', '$p_credit', '30')";
    }
  }
  else{
    echo mysql_error().$check_account;
  }
  echo "$sql0 \n";

  //Actual execution of query
  if (!$debug){
    if ( $res = mysql_query($sql0) ) {
      echo "Success!";
      //Check if any rows is affected. If there are rows affected, store the details and send an email
      $affected_rows = mysql_affected_rows();
      if($affected_rows != 0){
        $updated[] = "$affected_rows rows affected: ".$sql0;
      }
    }
   else{
      $sql_error = mysql_error()." : ".$sql0;
      $sql_errors[] = $sql_error;
   }
  }

//Campaign level
$baidu_campaignIDs = array();
$campaigns = $user['campaigns'];
echo "Number of campaigns ".count($campaigns)."\n";
foreach ($campaigns as $key => $value){

  //If end date is empty, set it as 2 years later                                                                                                   
  $endDT = $value['endDate'];
  if(empty($endDT) || $endDT == '' ){
    $timestamp = strtotime('+2 years');
    $value['endDate'] = date("Y-m-d", $timestamp);
  }

  $error=check_campaign_values($value, $external_accountID);
  //print_r($error);
  
  if(!empty($error['error_codes'])){
    $all_errors['campaigns'][] = $error;
    //If IAB errors, continue to let it create campaign
    if(count($error['error_codes'])==1 && isset($error['error_codes']['206'])){
    }
    else{
      continue;
    }
  }
 
  $external_campaignID = $value['campaignId'];
  $baidu_campaignIDs[] = $external_campaignID; 
  $campaignName = $value['campaignName'];
  $category = $value['category'];
  $IAB_Category = implode("|",$category);
  $campaign_dailyBudget = $value['dailyBudget'];
  $campaign_startdate = $value['startDate'];
  $campaign_enddate = $value['endDate'];
  $days_diff = ceil(abs(strtotime($campaign_enddate) - strtotime($campaign_startdate)) / 86400);
  $campaign_maxSpend = $campaign_dailyBudget * $days_diff;
  echo "days_diff: $days_diff \n";

  //If IAB contains games, the ad categories should include games as well 
  if(strstr($IAB_Category,"IAB9-30")){
    if(!strstr($adcategories, 'games') ){
      $adcategories = $adcategories." games";
    }
  }

  //If account is paused, pause the campaign so nothing will run 
  if($status=='Paused'){
  	$campaignstatus = 'Paused';
  }
  else{
  	$campaignstatus = $value['status'];	
  }

  //Get accountID 
  $accountID_sql = "select accountID from account where agencyAccountID = 10297 and external_accountID='$external_accountID'";
  //echo "get accountID: $accountID_sql \n";
  if ( $res = mysql_query($accountID_sql) ) {
    if ( $a = mysql_fetch_object($res) ) {
      $accountID = $a->accountID;
    }
  }
  else{
    $sql_error = mysql_error()." : ".$accountID_sql;
    $sql_errors[] = $sql_error;
  }

  //SQL for campaign 
  //Check if we should use update or insert, assuming external campaignID is unique
  $check_campaign = "select * from campaign where accountID='$accountID' and external_campaignID=$external_campaignID";
  echo "check campaign: $check_campaign \n";
  if ( $res = mysql_query($check_campaign) ) {
    //If campaign already existed
    if ( $a = mysql_fetch_object($res) ) {
      $existing_campaignID = $a->campaignID;
      $sql1 = "update campaign set lastUpdatedBy=Now(), campaignName='$campaignName', dayspendMax=$campaign_dailyBudget, totalspendMax=$campaign_maxSpend, campaignstartDT='$campaign_startdate', campaignendDT='$campaign_enddate', campaignstatus='$campaignstatus', iabcategories='$IAB_Category', external_campaignID='$external_campaignID', rtbok=1, qualityRequirement=10, adcategories='$adcategories'  where campaignID='$existing_campaignID' limit 1"; // accountID='$accountID' and external_campaignID=$external_campaignID and campaignID='$existing_campaignID' limit 1";
    }
    else{
        $sql1 = "insert into campaign (lastUpdatedBy, accountID, campaignName, totalspendMax, campaignstartDT, campaignendDT, campaignstatus, iabcategories, external_campaignID, dayspendMax, rtbok, qualityRequirement, adcategories) values (Now(), '$accountID', '$campaignName', $campaign_maxSpend, '$campaign_startdate', '$campaign_enddate', '$campaignstatus', '$IAB_Category', '$external_campaignID', $campaign_dailyBudget, 1, 10, '$adcategories')";
	//Send out an email if a campaign is created
	$message = "Campiagn $campaignName is created, external_campaignID is $external_campaignID. Account ID is $accountID.\n";
	$message .= "Link: http://dev.mdotm.com/account/campaigns/$accountID";
	mail('bing@mdotm.com, rodney@mdotm.com', 'Baidu campaign Created', $message, 'FROM: MDOTM');
    }
  }
  else{
    $sql_error = mysql_error()." : ".$check_campaign;
    $sql_errors[] = $sql_error;
  }

  echo "$sql1 \n";
  
  //Actual execution of query
  if (!$debug){
    if ( $res = mysql_query($sql1) ) {
      echo "Success!";

      //Check if any rows is affected. If there are rows affected, store the details and send an email
      $affected_rows = mysql_affected_rows();
      if($affected_rows != 0){
        $updated[] = "$affected_rows rows affected: ".$sql1;
      }

    }
    else{
      $sql_error = mysql_error()." : ".$sql1;
      $sql_errors[] = $sql_error;
    }
  }
  

  //group level 
  $baidu_groupIDs = array();
  $groups = $value['groups'];
  foreach ($groups as $k => $grp){
    $error=check_group_values($grp, $external_campaignID, $external_accountID);
    //Check for warnings (os targeting matches the url)
    $warnings[]=check_targeting_url_isValid($grp);

    if(!empty($error['error_codes'])){
      $all_errors['groups'][] = $error;
      continue;
    }

    $external_groupID = $grp['groupId'];
    $baidu_groupIDs[] = $external_groupID;
    $groupName = $grp['groupName'];
    $price = $grp['price']; //CPC
    $priceModel = strtolower($grp['priceModel']);
    $adgroupstatus = $grp['status'];

    //If price model is not cpc, do not run it
    if($priceModel != 'cpc'){
      //If it's cpa, set the ecpamax to be that value instead
      if($priceModel == 'cpa'){
	$priceModel = 'ecpamax';
      }
      else{
	$price = 0; 
      }
      $warning[] = "Adgroup wont run => Price model is not cpc";
    }

    //Allocate the max day spend, if not set, it will be default as 0 (no maximum)
    if($grp['allocatedBudget']){
      $adgroup_dayspendMax=$grp['allocatedBudget'];
    }
    else{
      $adgroup_dayspendMax=0;
    }
    
    //device targeting and os targeting
    $os = explode('|',$grp['osTargeting']);
    
    //Android device targeting
    if(stristr($os[0],'android')){
      $devicetargeting = 'Android';
      $mtargeting = $grp['deviceTargeting'];
    }

    //iOS device targeting
    else if (stristr($os[0],'ios')){
      if($grp['deviceTargeting']=='ALL'){
        $devicetargeting = 'iPhone|iPad|iPod';
      }
      else{
        $devicetargeting = $grp['deviceTargeting'];
      }
    }

  //os targeting 
  $ostargeting = '';
  $osVersions = array();
  foreach ($os as $k2 => $v2){
    $tmp = explode('-',$v2);

    //if has more than 1 decimals, trim it to 1 decimal place 
    $decimals = explode(".", $tmp[1]);
    //if more than 1 ".", take only the first one
    if (count($decimals) > 2 ){
      $tmp[1] = $decimals[0].".".$decimals[1];
    }

    //Get only distinct os versions
    if (!in_array($tmp[1], $osVersions) && strlen($tmp[1])!=0 ){
      $osVersions[] = $tmp[1];
    }

    /*
    //Get the string of os versions
    if($ostargeting!=''){
      $ostargeting = $ostargeting."|".$tmp[1];
    }
    else{
      $ostargeting = $tmp[1];
    }
    */

  }
  $ostargeting = implode('|', $osVersions);

  $geotargeting = $grp['geoTargeting'];
  if ($geotargeting == 'ALL'){
    $geotargeting = 'US|GB|CA|AU|FR|DE|JP';
  }
  $appid = $grp['appId'];
  //$priceModel = strtolower($grp['priceModel']);

  //Get campaignID 
  $campaignID_sql = "select campaignID from campaign where external_campaignID = '$external_campaignID' and accountID = '$accountID'";
  if ( $res = mysql_query($campaignID_sql) ) {
    if ( $a = mysql_fetch_object($res) ) {
      $campaignID = $a->campaignID;
    }
  }
  else{
    $sql_error = mysql_error()." : ".$campaignID_sql;
    $sql_errors[] = $sql_error;
  }

  //SQL for adgroup
  //Check if we should use update or insert, assuming external groupID is unique
  $check_adgroup = "select * from adgroup where campaignID='$campaignID' and external_adgroupID=$external_groupID and accountID='$accountID'";
  if ( $res = mysql_query($check_adgroup) ) {
    //If adgroup already existed
    if ( $a = mysql_fetch_object($res) ) {
      $existing_adgroupID = $a->adgroupID;
      $sql2 = "update adgroup set adgroup='$groupName', totalspendMax=$budget, startDT='$campaign_startdate', endDT='$campaign_enddate', $priceModel=$price, devicetargeting='$devicetargeting', ostargeting='$ostargeting', appid='$appid', accountID='$accountID', adgroupstatus='$adgroupstatus', campaignID=$campaignID, external_adgroupID='$external_groupID', targeting='$geotargeting', dayspendMax='$adgroup_dayspendMax' where adgroupID = '$existing_adgroupID' limit 1"; //campaignID='$campaignID' and external_adgroupID=$external_groupID and accountID = '$accountID' limit 1";
    }
    else{
      $sql2 = "insert into adgroup (adgroup, totalspendMax, startDT, endDt, $priceModel, devicetargeting, ostargeting, appid, accountID, adgroupstatus, campaignID, external_adgroupID, targeting, dayspendMax) values ('$groupName', $budget, '$campaign_startdate', '$campaign_enddate', $price, '$devicetargeting', '$ostargeting', '$appid', '$accountID', 'Active', $campaignID, '$external_groupID', '$geotargeting', '$adgroup_dayspendMax')";
    }
  }
  else{
    $sql_error = mysql_error()." : ".$check_adgroup;
    $sql_errors[] = $sql_error;
  }
 
  echo "$sql2 \n";
  
  //Actual execution of query
  if (!$debug){
    if ( $res = mysql_query($sql2) ) {
      echo "Success!";

      //Check if any rows is affected. If there are rows affected, store the details and send an email
      $affected_rows = mysql_affected_rows();
      if($affected_rows != 0){
        $updated[] = "$affected_rows rows affected: ".$sql2;
      }

    }
    else{
      $sql_error = mysql_error()." : ".$sql2;
      $sql_errors[] = $sql_error;
    }
  }

  //Ad level
  $current_ads = array();
  $ads= $grp['ads'];
  foreach ($ads as $k3 => $ad){
    $error=check_ad_values($ad, $external_groupID, $external_campaignID, $external_accountID);
   
    if(!empty($error['error_codes'])){
      $all_errors['ads'][] = $error;
      continue;
    }

    $external_adID = $ad['adId'];
    $adTitle = $ad['adTitle'];
    $adDescription = $ad['adDescription'];
    $graphicFile = $ad['picUrl'];
    $url = $ad['clickUrl'];
    $adstatus = $ad['status'];

    //Get adSize
    $size = getimagesize($graphicFile);
    $width = $size[0];
    $height = $size[1];
    $adSize = $width.'x'.$height;
    $footprint = md5(file_get_contents($graphicFile));

    //Get adgroupID 
    $adgroupID_sql = "select adgroupID from adgroup where external_adgroupID = '$external_groupID' and campaignID = '$campaignID' and accountID = '$accountID'";
    if ( $res = mysql_query($adgroupID_sql) ) {
      if ( $a = mysql_fetch_object($res) ) {
        $adgroupID = $a->adgroupID;
      }
    }
    else{
      $sql_error = mysql_error()." : ".$adgroupID_sql;
      $sql_errors[] = $sql_error;
    }

    //SQL for ad (need to add in adSize, footprint, graphicFileSize)
    //Check if we should use update or insert, assuming external groupID is unique
    $check_ad = "select * from ad where adgroupID='$adgroupID' and external_adID=$external_adID and accountID='$accountID'";
    if ( $res = mysql_query($check_ad) ) {
      //If ad already existed
      if ( $a = mysql_fetch_object($res) ) {
        $existing_adID = $a->adID;
        $sql3 = "update ad set lastUpdatedBy=Now(), accountID='$accountID', adTitle='$adTitle', adText='$adDescription', adLastUpdateDT=Now(), adSize='$adSize', adType=1, adgroup='$groupName', url='$url', status='$adstatus', graphicFile='$graphicFile', appid='$appid', footprint='$footprint', external_adID='$external_adID', dating=0, catchy=0, ambiguous=0 where adID='$existing_adID' limit 1"; //adgroupID = '$adgroupID' and accountID = '$accountID' and external_adID=$external_adID and adID='$adID' limit 1";
        $current_ads[] = $existing_adID;
      }
      else{
        //Create a new adID if it's an insert statement
        $adID = substr(md5(microtime().$external_adID.rand(1, 1000000)), 0, 8);
        $sql3 = "insert into ad (adID, lastUpdatedBy, adgroupID, accountID, adTitle, adText, adLastUpdateDT, adSize, adType, adgroup, url, status, graphicFile, creationDT, appid, footprint, external_adID, dating, catchy, ambiguous) values ('$adID', Now(), '$adgroupID', '$accountID', '$adTitle', '$adDescription', Now(), '$adSize', 1, '$groupName', '$url', '$adstatus', '$graphicFile', Now(), '$appid', '$footprint', '$external_adID', 0, 0, 0)";
        $current_ads[] = $adID;
      }
    }
    else{
      $sql_error = mysql_error()." : ".$check_ad;
      $sql_errors[] = $sql_error;
    }

    echo "$sql3 \n";
    //Execute query
  if (!$debug){
    if ( $res = mysql_query($sql3) ) {
      echo "Success!";

      //Check if any rows is affected. If there are rows affected, store the details and send an email
      $affected_rows = mysql_affected_rows();
      if($affected_rows != 0){
        $updated[] = "$affected_rows rows affected: ".$sql3;
      }

    }
    else{
      $sql_error = mysql_error()." : ".$sql3;
      $sql_errors[] = $sql_error;
    }
  }
  
  }

    //Select all ads in a particular adgroup, archive any ads that's not included in the given JSON object
    $ads_in_adgroup = "select * from ad where adgroupID='$adgroupID' and accountID='$accountID' and external_adID is not null";
    if ( $res0 = mysql_query($ads_in_adgroup) ) {
      while ( $a0 = mysql_fetch_object($res0) ) {
        $adID_in_check = $a0->adID;
        if(!in_array($adID_in_check, $current_ads)){
          $archive_ad = "update ad set status = 'Deleted' where adID = '$adID_in_check' limit 1";
          echo "Archive ads: $archive_ad \n";
	  //Execute query
          if (!$debug){
            if ($r = mysql_query($archive_ad)){
            }
            else{
              $sql_errors[] = $sql_error;
            }
          }

        }
      }
    }
    else{
      $sql_error = mysql_error()." : ".$ads_in_adgroup;
      $sql_errors[] = $sql_error;
    }


  }


    //Select all groups in a particular campaign, archive any ads that's not included in the given JSON object
    $adgroups_in_campaign = "select adgroupID, external_adgroupID from adgroup where campaignID='$campaignID' and accountID='$accountID' and external_adgroupID is not null";
    if ( $res1 = mysql_query($adgroups_in_campaign) ) {
      while ( $a1 = mysql_fetch_object($res1) ) {
        $adgroup_in_check = $a1->adgroupID;
        $external_adgroup_in_check = $a1->external_adgroupID;
        if(!in_array($external_adgroup_in_check, $baidu_groupIDs)){
          $archive_adgroup = "update adgroup set adgroupstatus = 'Deleted' where adgroupID = '$adgroup_in_check' limit 1";
          echo "Archive adgroup: $archive_adgroup \n";
	  //Execute query
          if (!$debug){
            if ($r = mysql_query($archive_adgroup)){
            }
            else{
              $sql_errors[] = $sql_error;
            }
          }

        }
      }
    }
    else{
      $sql_error = mysql_error()." : ".$adgroups_in_campaign;
      $sql_errors[] = $sql_error;
    }
  


}


    //Select all groups in a particular campaign, archive any ads that's not included in the given JSON object
    $campaigns_in_user = "select campaignID, external_campaignID from campaign where accountID='$accountID' and external_campaignID is not null";
    if ( $res2 = mysql_query($campaigns_in_user) ) {
      while ( $a2 = mysql_fetch_object($res2) ) {
        $campaign_in_check = $a2->campaignID;
        $external_campaign_in_check = $a2->external_campaignID;
        if(!in_array($external_campaign_in_check, $baidu_campaignIDs)){
          $archive_campaign = "update campaign set campaignstatus = 'Deleted' where campaignID = '$campaign_in_check' limit 1";
          echo "Archive campaign: $archive_campaign \n";
	  //Execute query
          if (!$debug){
            if ($r = mysql_query($archive_campaign)){
            }
            else{
              $sql_errors[] = $sql_error;
            }
          }

        }
      }
    }
    else{
      $sql_error = mysql_error()." : ".$campaigns_in_user;
      $sql_errors[] = $sql_error;
    }


}


    //Select all users, archive any users that's not included in the given JSON object
    $users_in_json = "select accountID, external_accountID from account where agencyAccountID=10297 and external_accountID is not null";
    if ( $res3 = mysql_query($users_in_json) ) {
      while ( $a3 = mysql_fetch_object($res3) ) {
        $user_in_check = $a3->accountID;
        $external_user_in_check = $a3->external_accountID;
        if(!in_array($external_user_in_check, $baidu_userIDs)){
          $archive_user = "update account set status = 'inactive' where accountID = '$user_in_check' limit 1";
	  echo "Archive user: $archive_user \n";
	  //Execute query
          if (!$debug){
            if ($r = mysql_query($archive_user)){
            }
            else{
              $sql_errors[] = $sql_error;
            }
          }

        }
      }
    }
    else{
      $sql_error = mysql_error()." : ".$users_in_json;
      $sql_errors[] = $sql_error;
    }
  



//Get the path to the baidu folder and remove any files with the same md5
$dirPath = "/var/www/vhosts/mdotm.com/httpdocs/baidu/";
if ($handle = opendir($dirPath)) {
  while (false !== ($file = readdir($handle))) {
    if ($file != "." && $file != "..") { // strip the current and previous directory items
      if (strstr($file,$md5)){
	//echo "$file \n";
	unlink($dirPath . $file); // you can add some filters here, aswell, to filter datatypes, file, prefixes, suffixes, etc
      }
    }
  }
  closedir($handle);
}
//print_r($all_errors);
$errors = json_encode($all_errors);
$warnings = json_encode($warnings);

$filename = "/var/www/vhosts/mdotm.com/httpdocs/baidu/mdotm-$date"."_"."$md5";
file_put_contents("$filename", print_r($errors,TRUE));

/*
$connection = ssh2_connect("admin", 22);
ssh2_auth_password($connection, 'baidu', 'b31jh31!');
$sftp = ssh2_sftp($connection);
if(ssh2_scp_send($connection,"/home/analytics/mdotm.com/analytics/bing/baidu/$date","/home/baidu/mdotm-$date", 0644)){
}
*/
//file_put_contents('ssh2.sftp://user:pass@example.com:22/path/to/filename');

if(count($updated) > 0){
  $message = print_r($updated,TRUE);
  mail('bing@mdotm.com, rodney@mdotm.com', 'Baidu API Rows affected', $message, 'FROM: MDOTM');
}

if(!empty($sql_errors)){
  $message = print_r($sql_errors,TRUE);
  //Add in the warnings in the error file 
  $message .= "\n";
  $message .= print_r($warnings, TRUE);
  mail('bing@mdotm.com, rodney@mdotm.com', 'Baidu SQL Error', $message, 'FROM: MDOTM');
} else {
  $message = "NO ERRORS - RODNEY TEST";
  //mail('bing@mdotm.com, rodney@mdotm.com', 'Baidu NO SQL Errors', $message, 'FROM: MDOTM');

}

?>
