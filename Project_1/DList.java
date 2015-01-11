import org.omg.CORBA.Current;


public class DList {
	protected DListNode head, tail;
	protected int size;
	
	//Constructor that creates an empty list
	public DList(){
		size =0;
		head = new DListNode(null, null);
		tail = new DListNode(head, null);
		head.setNext(tail);
	}
	
	//Returns the size of the list
	public int size(){
		return size;
	}
	
	//Checks if the list is empty
	public boolean isEmpty(){
		return (size==0);
	}
	
	//Returns the first node of the list
	public DListNode getFirst() throws IllegalStateException{
		if(isEmpty())
			throw new IllegalStateException("List is empty");
		return head.getNext();
	}

//Methods for debugging
/*
	public void printList() {
		DListNode curr = head;
		while (curr.next != null){
			curr = curr.next;
			System.out.println("Species: " + curr.getSpecies() + "Size: " + curr.getSize());
		}
	}
	
	public void printOcean() {
		DListNode curr = head;
		while (curr.next!=null){
			curr = curr.next;
			if (curr.getSpecies()==Ocean.FISH)
			System.out.print("|F" + curr.getSize() + "|");
			else if (curr.getSpecies()==Ocean.SHARK)
				System.out.print("|S" + curr.getSize() + "|");
			else
				System.out.print("|."+curr.getSize() + "|");
		}
	}
	*/
}
