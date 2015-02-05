<?
define("USERID_EXCEED_32CHAR_LIMIT", 101);
define("USERID_IS_NOT_NUMERIC", 102);
define("USERID_IS_EMPTY", 104);
define("USER_TOTAL_BUDGET_NOT_NUMERIC", 105);
define("USER_STATUS_INVALID", 106);
define("CAMPAIGNID_EXCEED_32CHAR_LIMIT", 201);
define("CAMPAIGNID_IS_NOT_NUMERIC", 202);
define("CAMPAIGNID_IS_EMPTY", 204);
define("CAMPAIGNNAME_EXCEED_64CHAR_LIMIT", 205);
define("CAMPAIGNCATEGORY_CONTAINS_INVALID_IAB", 206);
define("CAMPAIGN_DAILYBUDGET_IS_NOT_NUMERIC", 207);
define("CAMPAIGN_STARTDATE_LATER_THAN_ENDDATE", 208);
define("CAMPAIGN_STARTDATE_HAS_INVALID_FORMAT", 209);
define("CAMPAIGN_ENDDATE_EARLIER_THAN_STARTDATE", 210);
define("CAMPAIGN_ENDDATE_HAS_INVALID_FORMAT", 211);
define("CAMPAIGN_STATUS_INVALID", 212);
define("GROUPID_EXCEED_32CHAR_LIMIT", 301);
define("GROUPID_IS_NOT_NUMERIC", 302);
define("GROUPID_IS_EMPTY", 304);
define("GROUPNAME_EXCEED_64CHAR_LIMIT", 305);
define("GROUPPRICE_IS_NOT_NUMERIC", 306);
define("GROUP_OSTARGETING_EXCEED_64CHAR_LIMIT", 307);
define("GROUP_OSTARGETING_CONTAIN_IOS_AND_ANDROID", 308);
define("GROUP_OSTARGETING_HAS_INVALID_FORMAT", 309);
define("GROUP_OSTARGETING_IS_NOT_IOS_ANDROID", 310);
define("GROUP_DEVICETARGETING_EXCEED_16000CHAR_LIMIT", 311);
define("GROUP_GEOTARGETING_EXCEED_255CHAR_LIMIT", 312);
define("GROUP_APPID_EXCEED_128CHAR_LIMIT", 313);
define("GROUP_PRICEMODEL_INVALID", 314);
define("GROUP_STATUS_INVALID", 315);
define("GROUP_DEVICETARGETING_EXCEED_64CHAR_LIMIT", 316);
define("AD_ID_EXCEED_32CHAR_LIMIT",401);
define("ADID_IS_EMPTY",403);
define("AD_TITLE_EXCEED_1000CHAR_LIMIT", 404);
define("AD_DESCRIPTION_EXCEED_1000CHAR_LIMIT", 405);
define("AD_GRAPHIC_URL_IS_EMPTY", 406);
define("AD_GRAPHIC_URL_IS_NOT_VALID", 407);
define("AD_CLICK_URL_IS_EMPTY", 408);
define("AD_STATUS_INVALID", 409);

define("TARGETING_INCONSISTENT_WITH_URL", 501);

define("ERROR_ARRAY", serialize(array(USERID_EXCEED_32CHAR_LIMIT => "Error Code 101: UserID greater than max limit of 32 characters",
                                      USERID_IS_NOT_NUMERIC => "Error Code 102: UserID should only contain numeric values",
                                      USERID_IS_EMPTY => "Error Code 104: UserID should not be empty",
                                      USER_TOTAL_BUDGET_NOT_NUMERIC => "Error Code 105: User's total budget should only contain numeric values",
                                      USER_STATUS_INVALID => "Error Code 106: User's status should only be 'Active' or 'Paused'",
                                      CAMPAIGNID_EXCEED_32CHAR_LIMIT => "Error Code 201: CampaignID greater than max limit of 32 characters",
                                      CAMPAIGNID_IS_NOT_NUMERIC => "Error Code 202: CampaignID should only contain numeric values",
                                      CAMPAIGNID_IS_EMPTY => "Error Code 204: CampaignID should not be empty",
                                      CAMPAIGNNAME_EXCEED_64CHAR_LIMIT => "Error Code 205: Campaign Name greater than max limit of 64 characters",
                                      CAMPAIGNCATEGORY_CONTAINS_INVALID_IAB => "Error Code 206: Campaign Cateogry contains invalid or nonexisting IAB",
                                      CAMPAIGN_DAILYBUDGET_IS_NOT_NUMERIC => "Error Code 207: Campaign daily budget should only contain numeric values",
                                      CAMPAIGN_STARTDATE_LATER_THAN_ENDDATE => "Error Code 208: Campaign start date is later than the end date",
                                      CAMPAIGN_STARTDATE_HAS_INVALID_FORMAT => "Error Code 209: Campaign start date has an invalid date format",
                                      CAMPAIGN_ENDDATE_EARLIER_THAN_STARTDATE => "Error Code 210: Campaign end date is earlier than the start date",
                                      CAMPAIGN_ENDDATE_HAS_INVALID_FORMAT => "Error Code 211: Campaign end date has an invalid date format",
                                      CAMPAIGN_STATUS_INVALID => "Error Code 212: Campaign's status should only be 'Active' or 'Paused'",
                                      GROUPID_EXCEED_32CHAR_LIMIT => "Error Code 301: GroupID greater than max limit of 32 characters",
                                      GROUPID_IS_NOT_NUMERIC => "Error Code 302: GroupID should only contain numeric values",
                                      GROUPID_IS_EMPTY => "Error Code 304: GroupID should not be empty",
                                      GROUPNAME_EXCEED_64CHAR_LIMIT => "Error Code 305: Group Name greater than max limit of 64 characters",
                                      GROUPPRICE_IS_NOT_NUMERIC => "Error Code 306: Group price should only contain numeric values",
                                      GROUP_OSTARGETING_EXCEED_64CHAR_LIMIT => "Error Code 307: Group os targeting greater than max limit of 64 characters",
                                      GROUP_OSTARGETING_CONTAIN_IOS_AND_ANDROID => "Error Code 308: Group os targeting can only be ios or android, not both",
                                      GROUP_OSTARGETING_HAS_INVALID_FORMAT => "Error Code 309: Group os targeting has entries that are formatted incorrectly",
                                      GROUP_OSTARGETING_IS_NOT_IOS_ANDROID => "Error Code 310: Group os targeting is neither ios nor android",
                                      GROUP_DEVICETARGETING_EXCEED_16000CHAR_LIMIT => "Error Code 311: Group device targeting greater than max limit of 16000 characters (Android)",
                                      GROUP_GEOTARGETING_EXCEED_255CHAR_LIMIT => "Error Code 312: Group geo targeting greater than max limit of 255 characters",
                                      GROUP_APPID_EXCEED_128CHAR_LIMIT => "Error Code 313: Group app ID greater than max limit of 128 characters",
                                      GROUP_PRICEMODEL_INVALID => "Error Code 314: Group's price model should only be 'cpc', 'cpa', or 'cpm'",
                                      GROUP_STATUS_INVALID => "Error Code 315: Group's status should only be 'Active' or 'Paused'",
                                      GROUP_DEVICETARGETING_EXCEED_64CHAR_LIMIT => "Error Code 316: Group device targeting greater than max limit of 64 characters (iOS)",
                                      AD_ID_EXCEED_32CHAR_LIMIT => "Error Code 401: Ad ID greater than max limit of 32 characters",
                                      ADID_IS_EMPTY => "Error Code 403: Ad ID should not be empty",
                                      AD_TITLE_EXCEED_1000CHAR_LIMIT => "Error Code 404: Ad title greater than max limit of 1000 characters",
                                      AD_DESCRIPTION_EXCEED_1000CHAR_LIMIT => "Error Code 405: Ad description greater than max limit of 1000 characters",
                                      AD_GRAPHIC_URL_IS_EMPTY => "Error Code 406: Ad's pic url should not be empty",
                                      AD_GRAPHIC_URL_IS_NOT_VALID => "Error Code 407: Ad's pic url is invalid",
                                      AD_CLICK_URL_IS_EMPTY => "Error Code 408: Ad's click url should not be empty",
                                      AD_STATUS_INVALID => "Error Code 409: Ad's status should only be 'Active' or 'Paused'")));

define("WARNING_ARRAY", serialize(array(TARGETING_INCONSISTENT_WITH_URL => "Error Code 501: Click url is inconsistent with the appid, directing to the wrong app store")));

//--------------------Checks User level input--------------------
function check_user_id_length($userID){
  if(strlen($userID)>32){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[USERID_EXCEED_32CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_user_id_isNumeric($userID){
  if(!is_numeric($userID)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[USERID_IS_NOT_NUMERIC];
  }
  else{
    return 0;
  }
}

function check_user_id_isNotEmpty($userID){
  if(empty($userID)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[USERID_IS_EMPTY];
  }
  else{
    return 0;
  }
}

function check_user_total_budget_isNumeric($totalBudget){
  if(!is_numeric($totalBudget)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[USER_TOTAL_BUDGET_NOT_NUMERIC];
  }
  else{
    return 0;
  }
}

function check_user_status_isValid($userStatus){
  if($userStatus == 'Active' || $userStatus == 'Paused'){
    return 0; 
  }
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[USER_STATUS_INVALID];
  }
}

//--------------------Checks CAMPAIGN LEVEL input--------------------
function check_campaign_id_length($campaignID){
  if(strlen($campaignID)>32){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGNID_EXCEED_32CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_campaign_id_isNumeric($campaignID){
  if(!is_numeric($campaignID)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGNID_IS_NOT_NUMERIC];
  }
  else{
    return 0;
  }
}

function check_campaign_id_isNotEmpty($campaignID){
  if(empty($campaignID)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGNID_IS_EMPTY];
  }
  else{
    return 0;
  }
}

function check_campaignName_length($campaignName){
  if(strlen($campaignName)>64){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGNNAME_EXCEED_64CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_campaignCategory_isValid($campaignCategory){
  $iab_categories = get_iab_categories();
  $invalid_IAB = array();
  foreach($campaignCategory as $key => $val){
    $tmp_IAB = explode("-", $val);
    $IAB = $tmp_IAB[0];
    if(!array_key_exists("$IAB", $iab_categories) || !array_key_exists("$val",$iab_categories["$IAB"])){
        $invalid_IAB[] = $val;
    }
  }
  //No invalid IAB 
  if(empty($invalid_IAB)){
    return 0;
  }
  //return all the invalid IAB for checking 
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    $invalid_IAB_list = implode(",",$invalid_IAB);
    return $error_arr[CAMPAIGNCATEGORY_CONTAINS_INVALID_IAB]." : ".$invalid_IAB_list;
  }
}

function check_campaign_dailybudget_isNumeric($campaignDailyBudget){
  if(!is_numeric($campaignDailyBudget)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGN_DAILYBUDGET_IS_NOT_NUMERIC];
  }
  else{
    return 0;
  }
}

function check_campaign_startdate_later_than_enddate($campaignStartDate, $campaignEndDate){
  $startDate = strtotime($campaignStartDate);
  $endDate = strtotime($campaignEndDate);
  if($startDate > $endDate ){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGN_STARTDATE_LATER_THAN_ENDDATE];
  }
  else{
    return 0;
  }
}

function check_campaign_startdate_has_valid_format($campaignStartDate){
  $time = explode( "-",$campaignStartDate );
  $year = $time[0];
  $month = $time[1];
  $day = $time[2];

  if (checkdate($month, $day, $year)){
    return 0;
  }
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGN_STARTDATE_HAS_INVALID_FORMAT];
  }
}

function check_campaign_enddate_earlier_than_startdate($campaignStartDate, $campaignEndDate){
  $startDate = strtotime($campaignStartDate);
  $endDate = strtotime($campaignEndDate);
  if($endDate < $startDate){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGN_ENDDATE_EARLIER_THAN_STARTDATE];
  }
  else{
    return 0;
  }
}

function check_campaign_enddate_has_valid_format($campaignEndDate){
  $time = explode( "-",$campaignEndDate );
  $year = $time[0];
  $month = $time[1];
  $day = $time[2];

  if (checkdate($month, $day, $year)){
    return 0;
  }
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[CAMPAIGN_ENDDATE_HAS_INVALID_FORMAT];
  }
}

function check_campaign_status_isValid($campaignStatus){
  if($campaignStatus == 'Active' || $campaignStatus == 'Paused'){
    return 0;
  }
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[USER_STATUS_INVALID];
  }
}

//--------------------Checks GROUP LEVEL input--------------------
function check_group_id_length($groupID){
  if(strlen($groupID)>32){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUPID_EXCEED_32CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_group_id_isNumeric($groupID){
  if(!is_numeric($groupID)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUPID_IS_NOT_NUMERIC];
  }
  else{
    return 0;
  }
}

function check_group_id_isNotEmpty($groupID){
  if(empty($groupID)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUPID_IS_EMPTY];
  }
  else{
    return 0;
  }
}

function check_groupName_length($groupName){
  if(strlen($groupName)>64){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUPNAME_EXCEED_64CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_group_price_isNumeric($groupPrice){
  if(!is_numeric($groupPrice)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUPPRICE_IS_NOT_NUMERIC];
  }
  else{
    return 0;
  }
}

function check_group_osTargeting_length($group_osTargeting){
  //os targeting                                                                                                                                                 
  $os = explode('|',$group['osTargeting']);
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
    if (!in_array($tmp[1], $osVersions)){
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

  if(strlen($ostargeting)>64){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_OSTARGETING_EXCEED_64CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_group_osTargeting_devices($group_osTargeting){
  if(stristr($group_osTargeting, "Android") && stristr($group_osTargeting, "iOS")){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_OSTARGETING_CONTAIN_IOS_AND_ANDROID];
  }
  else{
    return 0;
  }
}

function check_group_osTargeting_format($group_osTargeting){
  $targeting_entries = explode("|", $group_osTargeting);
  $invalid_ostargeting_entries = array();
  foreach ($targeting_entries as $key => $val){
    $fields = explode("-", $val);
    $device = trim($fields[0]);
    $os = trim($fields[1]);

    //trim it to 1 decimal place 
    $decimals = explode(".", $os);
    //if more than 1 ".", take only the first one
    if (count($decimals) > 2 ){
      $os = $decimals[0].".".$decimals[1];
    }

    if(stristr($device, "Android") || stristr($device, "iOS")){
      if(!is_numeric($os) && strlen($os)!=0 ){
        $invalid_ostargeting_entries[] = $val;
      }
    }
    else{
      if(strlen($os)!=0){
	$invalid_ostargeting_entries[] = $val;
      }
    }
  }
  
  //No invalid osTargeting entry 
  if(empty($invalid_ostargeting_entries)){
    return 0;
  }
  //return all the invalid IAB for checking 
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    $invalid_ostargeting_entries_list = implode(",",$invalid_ostargeting_entries);
    return $error_arr[GROUP_OSTARGETING_HAS_INVALID_FORMAT]." : ".$invalid_ostargeting_entries_list;
  }
}

function check_group_osTargeting_devices_ios_android($group_osTargeting){
  if(!stristr($group_osTargeting, "Android") && !stristr($group_osTargeting, "iOS")){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_OSTARGETING_IS_NOT_IOS_ANDROID];
  }
  else{
    return 0;
  }
}

function check_group_devicetargeting_length($group_deviceTargeting, $group_osTargeting){
  if(stristr($group_osTargeting, "Android") && strlen($group_deviceTargeting)>16000){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_DEVICETARGETING_EXCEED_16000CHAR_LIMIT];
  }
  elseif(stristr($group_osTargeting, "iOS") && strlen($group_deviceTargeting)>64){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_DEVICETARGETING_EXCEED_64CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_group_geotargeting_length($group_geoTargeting){
    if(strlen($group_geoTargeting)>255){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_GEOTARGETING_EXCEED_255CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_group_appid_length($group_appid){
    if(strlen($group_appid)>128){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_APPID_EXCEED_128CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_group_pricemodel_isValid($group_priceModel){
  $priceModel = strtolower($group_priceModel);
  if($priceModel == 'cpc' || $priceModel == 'cpa' || $priceModel == 'cpm'){
    return 0;
  }
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_PRICEMODEL_INVALID];
  }
}

function check_group_status_isValid($group_status){
  if($group_status == 'Active' || $group_status == 'Paused'){
    return 0;
  }
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[GROUP_STATUS_INVALID];
  }
}

//--------------------Checks AD level input--------------------
function check_ad_id_length($adID){
    if(strlen($adID)>32){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[AD_ID_EXCEED_32CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_ad_id_isNotEmpty($adID){
  if(empty($adID)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[ADID_IS_EMPTY];
  }
  else{
    return 0;
  }
}

function check_ad_title_length($adTitle){
    if(strlen($adTitle)>1000){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[AD_TITLE_EXCEED_1000CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_ad_description_length($adDescription){
    if(strlen($adDescription)>1000){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[AD_DESCRIPTION_EXCEED_1000CHAR_LIMIT];
  }
  else{
    return 0;
  }
}

function check_ad_graphic_url_isNotEmpty($adGraphicUrl){
  if(empty($adGraphicUrl)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[AD_GRAPHIC_URL_IS_EMPTY];
  }
  else{
    return 0;
  }
}

function check_ad_graphic_url_isValid($adGraphicUrl){
  if(getimagesize($adGraphicUrl) !== false){
    return 0;
  }
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[AD_GRAPHIC_URL_IS_NOT_VALID];
  }
}

function check_ad_click_url_isNotEmpty($adClickUrl){
    if(empty($adClickUrl)){
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[AD_CLICK_URL_IS_EMPTY];
  }
  else{
    return 0;
  }
}

function check_ad_status_isValid($adStatus){
  if($adStatus == 'Active' || $adStatus == 'Paused'){
    return 0;
  }
  else{
    $error_arr = unserialize(ERROR_ARRAY);
    return $error_arr[AD_STATUS_INVALID]."=> invalid status given is: $userStatus";
  }
}

//--------------------Warning Cases (Campaigns will still be created)--------------------

function check_targeting_url_isValid($adgroup){

  $warnings = array();
  //Check if the appid is targeting ios or android
  $appid = $adgroup['appId'];
  if (strstr($appid, ".")){
    $device = 'android';
  }
  else{
    $device = 'ios';
  }

  foreach($adgroup['ads'] as $k => $ad){
    if ($device == 'android' && strstr($ad['clickUrl'], 'itunes.apple.com') ){
      $warning_arr = unserialize(WARNING_ARRAY);
      $warnings[] = $warning_arr[TARGETING_INCONSISTENT_WITH_URL]."=> adgroupID: ".$adgroup['groupId']." appid: $appid but url is ".$ad['clickUrl'];
    }
    elseif ($device == 'ios' && strstr($ad['clickUrl'], 'play.google.com') ){
      $warning_arr = unserialize(WARNING_ARRAY);
      $warnings[] = $warning_arr[TARGETING_INCONSISTENT_WITH_URL]."=> adgroupID: ".$adgroup['groupId']." appid: $appid but url is ".$ad['clickUrl'];
    }
  }

  return $warnings;
}


function check_user_values($user){
  $result = array();
  $final_result = array();
  $userID = $user['id'];
  $userName = $user['userName'];
  $totalBudget = $user['totalBudget'];
  $userStatus = $user['status'];
  
  /*
  //For testing 
  $userID = "123456789012345678901234567890123456789012345678901234567890";
  $totalBudget = '2k';
  $userStatus = 'hmm';
  */

  $result["error_codes"][] = check_user_id_length($userID);
  //Not required to be Numeric since they are using the externalID field
  //$result["error_codes"][] = check_user_id_isNumeric($userID);
  $result["error_codes"][] = check_user_id_isNotEmpty($userID);
  $result["error_codes"][] = check_user_total_budget_isNumeric($totalBudget);
  $result["error_codes"][] = check_user_status_isValid($userStatus);

  $result["error_codes"]=array_filter($result["error_codes"]);

  //Change to using the Error Code as the key 
  $final_result["user_id"] = $userID;

  foreach($result["error_codes"] as $k => $v){
    $tmp = explode(": ", $v);
    $tmp2 = array_slice($tmp, 1, count($tmp));
    $error_des = implode(": ",$tmp2);
    $code_number = trim(str_replace('Error Code','',$tmp[0]));
    $new_key = $code_number;
    $final_result["error_codes"][$new_key] = $error_des;
  }
  return $final_result;
}

function check_campaign_values($campaign, $userID){
  $result = array();
  $final_result = array();
  $campaignID = $campaign['campaignId'];
  $campaignName = $campaign['campaignName'];
  $campaignCategory = $campaign['category'];
  $campaignDailyBudget = $campaign['dailyBudget'];
  $campaignStartDate = $campaign['startDate'];
  $campaignEndDate = $campaign['endDate'];
  $campaignStatus = $campaign['status'];

  /*
  //Testing
  $campaignID = 'acscsscd';
  $campaignName = 'andsndsindisndsunduindiandisunduaisdnisundusdnisncijscnisncuisnisndiundiuan';
  $campaignDailyBudget = 'one thousand';
  $campaignStartDate = '2013-11-20';
  $campaignEndDate = '2013-10-30';
  $campaignStatus = '';
  */

  $result["error_codes"][] = check_campaign_id_length($campaignID);
  //Not required to be Numeric since they are using the externalID field 
  //$result["error_codes"][] = check_campaign_id_isNumeric($campaignID);
  $result["error_codes"][] = check_campaign_id_isNotEmpty($campaignID);
  $result["error_codes"][] = check_campaignName_length($campaignName);
  //Commented out for now so campaign is created and we can manually add in domain etc 
  //$result["error_codes"][] = check_campaignCategory_isValid($campaignCategory);
  $result["error_codes"][] = check_campaign_dailybudget_isNumeric($campaignDailyBudget);
  $result["error_codes"][] = check_campaign_startdate_later_than_enddate($campaignStartDate, $campaignEndDate);
  $result["error_codes"][] = check_campaign_startdate_has_valid_format($campaignStartDate);
  $result["error_codes"][] = check_campaign_enddate_earlier_than_startdate($campaignStartDate, $campaignEndDate);
  $result["error_codes"][] = check_campaign_enddate_has_valid_format($campaignEndDate);
  $result["error_codes"][] = check_campaign_status_isValid($campaignStatus);

  $result["error_codes"]=array_filter($result["error_codes"]);

  //Change to using the Error Code as the key 
  $final_result["user_id"] = $userID;
  $final_result["campaign_id"] = $campaignID;

  foreach($result["error_codes"] as $k => $v){
    $tmp = explode(": ", $v);
    $tmp2 = array_slice($tmp, 1, count($tmp));
    $error_des = implode(": ",$tmp2);
    $code_number = trim(str_replace('Error Code','',$tmp[0]));
    $new_key = $code_number;
    $final_result["error_codes"][$new_key] = $error_des;
  }
  return $final_result;
}

function check_group_values($group, $campaignID, $userID){

  //os targeting          
  $os = explode('|',$group['osTargeting']);
  $ostargeting = '';
  foreach ($os as $k2 => $v2){
    $tmp = explode('-',$v2);
    //Get the string of os versions                                                                                                                                             
    if($ostargeting!=''){
      $ostargeting = $ostargeting."|".$tmp[1];
    }
    else{
      $ostargeting = $tmp[1];
    }
  }


  $result = array();
  $final_result = array();
  $groupID = $group['groupId'];
  $groupName = $group['groupName'];
  $groupPrice = $group['price'];
  $group_osTargeting = $group['osTargeting'];
  $group_deviceTargeting = $group['deviceTargeting'];
  $group_geoTargeting = $group['geoTargeting'];
  $group_appid = $group['appId'];
  $group_priceModel = $group['priceModel'];
  $group_status = $group['status'];



  /*
  //Testing 
  $groupID = 'abc123';
  $groupName = 'nvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuosnvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuos';
  $groupPrice = 'abc';
  $group_osTargeting = 'ios-2.3|android-2.1|android-3.3';
  $group_deviceTargeting = '';
  $group_geoTargeting = 'nvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuosnvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuosnvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuosnvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuosnvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuosnvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuos';
  $group_appid = '';
  $group_priceModel = 'You pick';
  $group_status = '';
  */

  $result["error_codes"][] = check_group_id_length($groupID);
  //Not required to be Numeric since they are using the externalID field 
  //$result["error_codes"][] = check_group_id_isNumeric($groupID);
  $result["error_codes"][] = check_group_id_isNotEmpty($groupID);
  $result["error_codes"][] = check_groupName_length($groupName);
  $result["error_codes"][] = check_group_price_isNumeric($groupPrice);
  $result["error_codes"][] = check_group_osTargeting_length($group_osTargeting);
  $result["error_codes"][] = check_group_osTargeting_devices($group_osTargeting);
  $result["error_codes"][] = check_group_osTargeting_format($group_osTargeting);
  $result["error_codes"][] = check_group_osTargeting_devices_ios_android($group_osTargeting);
  $result["error_codes"][] = check_group_devicetargeting_length($group_deviceTargeting, $group_osTargeting);
  $result["error_codes"][] = check_group_geotargeting_length($group_geoTargeting);
  $result["error_codes"][] = check_group_appid_length($group_appid);
  $result["error_codes"][] = check_group_pricemodel_isValid($group_priceModel);
  $result["error_codes"][] = check_group_status_isValid($group_status);

  $result["error_codes"]=array_filter($result["error_codes"]);

  //Change to using the Error Code as the key
  $final_result["user_id"] = $userID;
  $final_result["campaign_id"] = $campaignID;
  $final_result["group_id"] = $groupID; 

  foreach($result["error_codes"] as $k => $v){
    $tmp = explode(": ", $v);
    $tmp2 = array_slice($tmp, 1, count($tmp));
    $error_des = implode(": ",$tmp2);
    $code_number = trim(str_replace('Error Code','',$tmp[0]));
    $new_key = $code_number;
    $final_result["error_codes"][$new_key] = $error_des;
  }
  return $final_result;
}

function check_ad_values($ad, $groupID, $campaignID, $userID){
  $result = array();
  $final_result = array();
  $adID = $ad['adId'];
  $adTitle = $ad['adTitle'];
  $adDescription = $ad['adDescription'];
  $adGraphicUrl = $ad['picUrl'];
  $adClickUrl = $ad['clickUrl'];
  $adStatus = $ad['status'];

  /*
  //Testing 
  $adID = '1nnnwne9nd9wnd9uwebn9uwcu9bcn9ewnv9uwnbvu9wvnb9wnfbwbnwybcf9webnu9wenv9euwnbvu9bnvw';
  $adTitle = 'wenwocnweoucnoewncocnoewnucuoncfofncouencfouwenvouwenbvownvouwnvw';
  $adDescription = 'nvonvodnsvodnouvnbdounvosunvosdnvosnvosdnvosdnvosnvoudsnvosnvousdnvosunvuos';
  $adGraphicUrl = 'www.mdotm.com';
  $adClickUrl = '';
  $adStatus = 'What?';
  */

  $result["error_codes"][] = check_ad_id_length($adID);
  $result["error_codes"][] = check_ad_id_isNotEmpty($adID);
  $result["error_codes"][] = check_ad_title_length($adTitle);
  $result["error_codes"][] = check_ad_description_length($adDescription);
  $result["error_codes"][] = check_ad_graphic_url_isNotEmpty($adGraphicUrl);
  $result["error_codes"][] = check_ad_graphic_url_isValid($adGraphicUrl);
  $result["error_codes"][] = check_ad_click_url_isNotEmpty($adClickUrl);
  $result["error_codes"][] = check_ad_status_isValid($adStatus);

  $result["error_codes"]=array_filter($result["error_codes"]);

  //Change to using the Error Code as the key 
  $final_result["user_id"] = $userID;
  $final_result["campaign_id"] = $campaignID;
  $final_result["group_id"] = $groupID;
  $final_result["ad_id"] = $adID;

  foreach($result["error_codes"] as $k => $v){
    $tmp = explode(": ", $v);
    $tmp2 = array_slice($tmp, 1, count($tmp));
    $error_des = implode(": ",$tmp2);
    $code_number = trim(str_replace('Error Code','',$tmp[0]));
    $new_key = $code_number;
    $final_result["error_codes"][$new_key] = $error_des;
  }
  return $final_result;
}

?>
