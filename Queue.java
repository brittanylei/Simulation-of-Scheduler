//-----------------------------------------------------------------------------
// Brittany Lei bclei
// Tian Lun Lee tlee60
// 12B: PA4
// Queue.java
// Creates a Queue ADT using a private Node class and a linked list structure
//-----------------------------------------------------------------------------

public class Queue implements QueueInterface{

   private class Node {
     Object item;
     Node next;

     Node (Object o) {
       item = o;
       next = null;
     }
   }

   private Node head;
   private Node tail;
   private int numItems;

   public Queue() {
     head = null;
     tail = null;
     numItems = 0;
   }


   // isEmpty()
   // pre: none
   // post: returns true if this Queue is empty, false otherwise
   public boolean isEmpty() {
     return (numItems == 0);
   }

   // length()
   // pre: none
   // post: returns the length of this Queue.
   public int length() {
     return numItems;
   }

   // enqueue()
   // adds newItem to back of this Queue
   // pre: none
   // post: !isEmpty()
   public void enqueue(Object newItem) {
     Node N = new Node(newItem);
     if (head==null) {
       head = N;
     }
     else {
       tail.next = N;
     }
     tail = N;

     numItems++;
   }

   // dequeue()
   // deletes and returns item from front of this Queue
   // pre: !isEmpty()
   // post: this Queue will have one fewer element
   public Object dequeue() throws QueueEmptyException {
     if( numItems==0 ){
        throw new QueueEmptyException("cannot dequeue() empty queue");
     }
     Object returnOb = head.item;
     head = head.next;
     numItems--;
     return returnOb;
   }

   // peek()
   // pre: !isEmpty()
   // post: returns item at front of Queue
   public Object peek() throws QueueEmptyException {
     if( numItems==0 ){
        throw new QueueEmptyException("cannot peek() empty queue");
     }
     return head.item;
   }

   // dequeueAll()
   // sets this Queue to the empty state
   // pre: !isEmpty()
   // post: isEmpty()
   public void dequeueAll() throws QueueEmptyException {
     if( numItems==0 ){
        throw new QueueEmptyException("cannot dequeueAll() empty queue");
     }
     head = null;
     tail = null;
     numItems = 0;
   }

   // toString()
   // overrides Object's toString() method
   public String toString() {
     StringBuffer sb = new StringBuffer();
     for(Node N = head; N!=null; N = N.next) {
       sb.append(N.item).append(" ");
     }

     return new String(sb);
   }
}
