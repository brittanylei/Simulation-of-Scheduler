//-----------------------------------------------------------------------------
// Brittany Lei bclei
// Tian Lun Lee tlee60
// 12B: PA4
// Simulation.java
//
// This program takes an input file argument and runs a Job Simulation then outputs
// a report file and a trace file
//-----------------------------------------------------------------------------
import java.io.*;
import java.util.Scanner;

public class Simulation{

//-----------------------------------------------------------------------------
//
// The following function may be of use in assembling the initial backup and/or
// storage queues.  You may use it as is, alter it as you see fit, or delete it
// altogether.
//
//-----------------------------------------------------------------------------

  public static Job getJob(Scanner in) {
    String[] s = in.nextLine().split(" ");
    int a = Integer.parseInt(s[0]);
    int d = Integer.parseInt(s[1]);
    return new Job(a, d);
  }

  public static void main(String[] args) throws IOException{

//    1.  check command line arguments
    if(args.length < 1){
      System.out.println("Usage: Simulation <input file>");
      System.exit(1);
    }

//    2.  open files for reading and writing
    Scanner in = new Scanner(new File(args[0]));
    PrintWriter report = new PrintWriter(new FileWriter(args[0] + ".rpt"));
    PrintWriter trace = new PrintWriter(new FileWriter(args[0] + ".trc"));

    report.println("Report file: " + args[0] + ".rpt");
    int m = in.nextInt();
    in.nextLine();
    report.println(m + " Jobs:");

//    3.  read in m jobs from input file
    Queue store = new Queue();
    for (int i = 0; i < m; i++) {
      store.enqueue(getJob(in));
    }
    report.println(store);

    report.println("***********************************************************");
//    4.  run simulation with n processors for n=1 to n=m-1  {
    for (int n=1; n<m; n++){

//    5.      declare and initialize an array of n processor Queues and any
//            necessary storage Queues
      Queue[] procQ = new Queue[n+1];

      for (int i = 0; i < procQ.length; i++) {
        procQ[i] = new Queue();
      }

      for (int i = 0; i < m; i++) {
        Job temp = (Job)store.dequeue();
        store.enqueue(new Job(temp.getArrival(), temp.getDuration()));
        procQ[0].enqueue(temp);
      }

      int time = 0;

      trace.println("*****************************");
      String end = "s:";
      if (n>1) {
        end = ":";
      }
      trace.println(n + " processor" + end);
      trace.println("*****************************");
      trace.println("time=" + time);
      trace.println("0: " + procQ[0]);
      for (int i = 1; i < procQ.length; i++) {
        trace.println(i + ": " + procQ[i]);
      }
      trace.println();

  //    6.      while unprocessed jobs remain  {
      while (jobsRemain(procQ[0], m)) {
  //    7.      determine the time of the next arrival or finish event and
  //            update time
        int arriv = (int)Double.MAX_VALUE;
        if (!procQ[0].isEmpty() && ((Job)procQ[0].peek()).getFinish()==-1) {
          arriv = ((Job)procQ[0].peek()).getArrival();
        }
        int fin=(int)Double.MAX_VALUE;

        if (!arrayIsEmpty(procQ)) {
          for (int i = 1; i < procQ.length; i++) {
            if (!procQ[i].isEmpty() && ((Job)procQ[i].peek()).getFinish() < fin) {
              fin = ((Job)procQ[i].peek()).getFinish();
            }
          }
        }

        if (fin <= arriv) {
          time = fin;
        }
        else {
          time = arriv;
        }

  //    8.          complete all processes finishing now
        for (int job = 1; job < procQ.length; job++) {
          if (!procQ[job].isEmpty()) {
            Job j1 = (Job)procQ[job].peek();
            if (j1.getFinish()<=time) {
              procQ[0].enqueue(j1);
              procQ[job].dequeue();

              if (!procQ[job].isEmpty()) {
                Job first = (Job)procQ[job].peek();
                if (first.getFinish()==-1)
                  first.computeFinishTime(time);
              }
            }
          }
        }

  //    9.          if there are any jobs arriving now, assign them to a processor
  //                Queue of minimum length and with lowest index in the queue array.
          for (int j = 0; j < procQ[0].length(); j++) {
            Job arrive = (Job)procQ[0].peek();
            if (arrive.getArrival() == time) {
              int shortest = shortestQ(procQ);

              procQ[shortest].enqueue(arrive);
              procQ[0].dequeue();

              // update finish time
              Job first = (Job)procQ[shortest].peek();
              if (first.getFinish()==-1) {
                first.computeFinishTime(time);
              }
            }
          }
          trace.println("time=" + time);
          for (int i = 0; i < procQ.length; i++) {
            trace.println(i + ": " + procQ[i]);
          }
          trace.println();

       //    10.     } end loop
      }

  //    11.     compute the total wait, maximum wait, and average wait for
  //            all Jobs, then reset finish times

      int totalWait = 0;
      int maxWait = 0;
      for (int i = 0; i < m; i++) {
        int temp = ((Job)procQ[0].peek()).getWaitTime();
        totalWait += temp;
        if (temp > maxWait) {
          maxWait = temp;
        }
        procQ[0].dequeue();
      }
      double avgWait = (double)totalWait/m;

      end = "s: ";
      if (n == 1) {
        end = ": ";
      }
      report.println(n + " processor" + end + " totalWait="+ totalWait
        + ", maxWait=" + maxWait + ", averageWait="+avgWait);
//    12. } end loop
    }

//    13. close input and output files
    in.close();
    report.close();
    trace.close();
  }
  // jobsRemain()
  // returns true if q contains fewer than m completed jobsRemain
  // returns false if all m jobs in q are complete
  private static boolean jobsRemain(Queue q, int m) {
    if ((!q.isEmpty() && ((Job)q.peek()).getFinish()==-1 ) || q.length()!=m) {
      return true;
    }
    return false;
  }

  // shortestQ()
  // returns the processor number with the fewest jobs
  private static int shortestQ(Queue[] q) {
    int shortest = 1;
    int shortestLength = q[1].length();
    for (int i = 2; i < q.length; i++) {
      if (shortestLength > q[i].length()) {
        shortestLength = q[i].length();
        shortest = i;
      }
    }
    return shortest;
  }

  // arrayIsEmpty()
  // returns true if every processor queue is empty
  private static boolean arrayIsEmpty(Queue[] q) {
    for (int i = 1; i < q.length; i++) {
      if (!q[i].isEmpty()) {
        return false;
      }
    }
    return true;
  }
}
