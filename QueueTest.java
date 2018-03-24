//-----------------------------------------------------------------------------
// Brittany Lei bclei
// Tian Lun Lee tlee60
// 12B: PA4
// QueueTest.java
// Tests the functionality of the Queue ADT
//-----------------------------------------------------------------------------
public class QueueTest {
  public static void main(String[] args) {
    Queue A = new Queue();
    // test isEmpty();
    System.out.println("Is empty: true; " + A.isEmpty());
    // test enqueue()
    A.enqueue(new Job(2,2));
    A.enqueue(new Job(3,5));
    A.enqueue(new Job(4,6));
    A.enqueue(new Job(7,10));
    A.enqueue(new Job(8,21));
    // test length()
    System.out.println("Length: 5; " + A.length());
    // test toString()
    System.out.println(A);
    System.out.println("Is empty: false; " + A.isEmpty());
    // test peek()
    System.out.println("peek: " + A.peek());

    // test dequeue()
    A.dequeue();
    A.dequeue();
    A.dequeue();
    System.out.println("peek: " + A.peek());
    System.out.println(A);

    A.dequeueAll();
    // test QueueEmptyException
    try{
       System.out.println(A.peek());
    }catch(QueueEmptyException e){
       System.out.println("Caught Exception: ");
       System.out.println(e);
       System.out.println("Continuing without interuption");
    }
    System.out.println("Is empty: true; " + A.isEmpty());
    System.out.println("Length: 0; " + A.length());


  }
}
