// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.*;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results Run(int runtime, Vector<Process> processVector) {
    int comptime = 0;

    if(processVector.isEmpty()) {
      throw new IllegalArgumentException("Process vector is empty");
    }

    if(!checkIsSystemSchedulable(processVector)) {
      throw new IllegalArgumentException("System is not schedulable with EDF");
    }
    String resultsFile = "Summary-Processes";
    int lcm = 1;
    for(Process process: processVector) {
      lcm = lcm(lcm, process.timePeriod);
    }
    Map<Integer, Integer> executionAmountMap = new HashMap<>();
    for (Process process: processVector) {
      executionAmountMap.put(processVector.indexOf(process), lcm / process.timePeriod);
    }
    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      Process process = processVector.stream().min(Comparator.comparingInt(p -> p.deadline)).get();
      out.println("Process: " + processVector.indexOf(process) + " registered... (" + process.releaseTime + " " + process.executionTime + " " + process.deadline + " " + process.timePeriod +  " " + process.cpuTime + ")");
      int processExecutionTime = 0;
      while (comptime < runtime) {
        if(processExecutionTime == process.executionTime) {
          processExecutionTime = 0;
          ++process.releaseTime;
          out.println("Process: " + processVector.indexOf(process) + " completed... (" + process.releaseTime + " " + process.executionTime + " " + process.deadline + " " + process.timePeriod +  " " + process.cpuTime + ")");
          while (
                  processVector.stream().anyMatch(p -> p.releaseTime < executionAmountMap.get(processVector.indexOf(p)))
                          && !isProcessReadyToStart(processVector, comptime)
          ) {
            ++comptime;
          }
          int currentComputationTime = comptime;
          process = processVector.stream()
                  .filter(p -> currentComputationTime >= p.releaseTime * p.timePeriod)
                  .filter(p -> p.releaseTime < executionAmountMap.get(processVector.indexOf(p)))
                  .min(Comparator.comparingInt(p -> (p.releaseTime + 1) * p.deadline)).orElse(null);
          if(process == null) {
            break;
          }
          out.println("Process: " + processVector.indexOf(process) + " registered... (" + process.releaseTime + " " + process.executionTime + " " + process.deadline + " " + process.timePeriod +  " " + process.cpuTime + ")");
        } else {
          ++processExecutionTime;
          ++comptime;
          ++process.cpuTime;
        }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return new Results("Dynamic priority", "EDF", comptime);
  }

  private static int gcd(int a, int b) {
    if (b == 0)
      return a;
    else
      return gcd(b, a % b);
  }

  private static int lcm(int a, int b)
  {
    return Math.abs(a * b) / gcd(a, b);
  }

  private static boolean checkIsSystemSchedulable(Vector<Process> processVector) {
    int lcm = 1;
    for (Process process: processVector) {
      lcm = lcm(lcm, process.deadline);
    }
    int counter = 0;
    for (Process process: processVector) {
      counter += process.executionTime * (lcm / process.deadline);
    }
    return counter <= lcm;
  }

  private static boolean isProcessReadyToStart(Vector<Process> processVector, int comptime) {
    return processVector.stream().anyMatch(p -> comptime >= p.releaseTime * p.timePeriod);
  }
}
