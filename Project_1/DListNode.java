
public class DListNode {
	protected int species, size, hunger, size2;
	protected DListNode next, prev;
	
	public DListNode (int type, int length, int hungry, DListNode n, DListNode p){
		species = type;
		size = length;
		size2 = length;
		hunger = hungry;
		next = n;
		prev = p;
	}
	
	public DListNode (int type, int length, DListNode n, DListNode p){
		species = type;
		size = length;
		size2 = length;
		hunger = 0;
		next = n;
		prev = p;
	}
	
	public DListNode (DListNode n, DListNode p){
		species = 0;
		size = 0;
		size2 = 0;
		hunger = 0;
		next = n;
		prev = p;
	}
	
	public int getSpecies(){
		return species;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getSize2(){
		return size2;
	}
	
	public int getHunger(){
		return hunger;
	}
	
	public DListNode getNext(){
		return next;
	}
	
	public DListNode getPrev(){
		return prev;
	}
	
	public void setSpecies(int newSpecies){
		species = newSpecies;
	}
	
	public void setSize(int newSize){
		size = newSize;
	}
	
	public void setSize2(int newSize2){
		size2 = newSize2;
	}
	
	public void setHunger(int newHunger){
		hunger = newHunger;
	}
	
	public void setNext(DListNode newNext){
		next = newNext;
	}
	
	public void setPrev(DListNode newPrev){
		prev = newPrev;
	}
}
