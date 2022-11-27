// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results Run(int runtime, Vector<sProcess> processVector, Results result) {
    int currentProcessNum;
    int comptime = 0;

    if(processVector.isEmpty()) {
      throw new IllegalArgumentException("Process vector is empty");
    }

    Queue<sProcess> processQueue = new PriorityQueue<>(Comparator.comparingInt(process -> process.deadline));

    for(sProcess process: processVector) {
      processQueue.offer(process);
    }

    String resultsFile = "Summary-Processes";

    result.schedulingType = "Batch (Nonpreemptive)";
    result.schedulingName = "First-Come First-Served";

    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      sProcess process = processQueue.poll();
      currentProcessNum = processVector.indexOf(process);
      out.println("Process: " + currentProcessNum + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + " " + process.deadline + ")");
      while (comptime < runtime) {
        if(comptime > process.deadline) {
          throw new RuntimeException("Process' deadline is missed: comptime is " + comptime + ", deadline is " + process.deadline);
        }
        if (process.cpudone == process.cputime) {
          out.println("Process: " + currentProcessNum + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + " " + process.deadline + ")");
          if (processQueue.isEmpty()) {
            result.compuTime = comptime;
            out.close();
            return result;
          }
          process = processQueue.poll();
          currentProcessNum = processVector.indexOf(process);
          out.println("Process: " + currentProcessNum + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + " " + process.deadline + ")");
        }      
        if (process.ioblocking == process.ionext) {
          out.println("Process: " + currentProcessNum + " I/O blocked... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + " " + process.deadline + ")");
          process.numblocked++;
          process.ionext = 0;
          processQueue.offer(process);
          process = processQueue.poll();
          currentProcessNum = processVector.indexOf(process);
          out.println("Process: " + currentProcessNum + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + " " + process.cpudone + " " + process.deadline + ")");
        }        
        process.cpudone++;       
        if (process.ioblocking > 0) {
          process.ionext++;
        }
        comptime++;
      }
      out.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    result.compuTime = comptime;
    return result;
  }
}
