
public class SinglyLinkedList implements List {

// necessary attributes for managing a linked list 

	
	private static class node{
		int data;
		node next;
		
		// generated constructor that creates an empty linked list 
		public node(int data, node next) {
			super();
			this.data = data;
			this.next = next;
		} 
		
		public void setNext(node n) {
			next=n;
			
		}
		
		public int getData() {
			return data;
		}
		
		 public node getNext(){
		  return next;
		 }
		 
		
	}

	//instance variables 
	
	private node head = null; 
	private node tail= null;
	private int size = 0;
	//private int idx = 0; 
	public SinglyLinkedList() {}
			


// implementing the methods 
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}
	
	
//??	
	public int get(int idx){
			
		node temporary = head;
		for (int i = 0; i<=idx;i++) {
			
			temporary = temporary.getNext();
			
		}
		
		 return temporary.getData();

	}

	
	public void add(int data) {
		
		node newest = new node(data, null);
		if (isEmpty()) {
			head = newest;
		}
		else {
			tail.setNext(newest);
			
		}
		tail=newest;
		size = size +1;
		
	}
	public void add(int idx, int data){
		
	// if the list is empty
		if (head == null) {
			head = new node(data, null);
		}
	// if adding at front of list	
		else if (idx == 0) {
			// make a new node with assigned data 
			node nodeToAdd = new node(data, null);
			// make the head the new nodes next 
			nodeToAdd.next=head; 
			// make the new node the now head
			head=nodeToAdd;
			
		}
		
		else {
			
			node temporary = head;
			for (int i = 0; i<=idx;i++) {
				
				temporary = temporary.getNext();
				
			}
			
		node newNode = new node(data, null);
		newNode.next=temporary.next;
		temporary.setNext(newNode);
			
		}
		
		size = size +1;
		
		
	}
	
	public int remove(int idx){
		
		if (idx == 0) {
			return head.getData(); 
		
		}
		
		else {
			
			node temporary = head;
			for (int i = 0; i<=idx;i++) {
				
				temporary = temporary.getNext();
				
			}
			temporary.setNext(temporary.getNext().getNext());
			 return temporary.getData();
			 
			 
		}
		
		size = size-1;
		
		
	}
	
	
}

/* maybe find put 
node temporary = head;
for (int i = 0; i<=idx;i++) {
	
	temporary = temporary.getNext();
	
}
in a method?? 
*/