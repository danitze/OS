// This file contains the main() function for the Scheduling
// simulation.  Init() initializes most of the variables by
// reading from a provided file.  SchedulingAlgorithm.Run() is
// called from main() to run the simulation.  Summary-Results
// is where the summary results are written, and Summary-Processes
// is where the process scheduling summary is written.

// Created by Alexander Reeder, 2001 January 06

import java.io.*;
import java.util.*;

public class Scheduling {
  private static int runtime = 1000;
  private static final Vector<Process> processVector = new Vector<>();
  private static final String resultsFile = "Summary-Results";

  private static void Init(String file) {
    File f = new File(file);
    String line;

    try {
      DataInputStream in = new DataInputStream(new FileInputStream(f));
      int i = 1;
      while ((line = in.readLine()) != null) {
        if (line.startsWith("process")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          processVector.addElement(
                  new Process(
                          Integer.parseInt(st.nextToken()),
                          Integer.parseInt(st.nextToken()),
                          Integer.parseInt(st.nextToken())
                  )
          );
        }
        if (line.startsWith("runtime")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          runtime = Common.s2i(st.nextToken());
        }
      }
      in.close();
    } catch (IOException e) { /* Handle exceptions */ }
  }

  private static void debug() {
    int size = processVector.size();
    for (int i = 0; i < size; i++) {
      System.out.println(i + " " + processVector.elementAt(i));
    }
    System.out.println("runtime " + runtime);
  }

  public static void main(String[] args) {
    int i;
    String inputFileName = "/Users/danilandreev/Documents/IntelliJProjects.nosync/os/sched/scheduling.conf";
    File f = new File(inputFileName);
    if (!(f.exists())) {
      System.out.println("Scheduling: error, file '" + f.getName() + "' does not exist.");
      System.exit(-1);
    }
    if (!(f.canRead())) {
      System.out.println("Scheduling: error, read of " + f.getName() + " failed.");
      System.exit(-1);
    }
    System.out.println("Working...");
    Init(inputFileName);
    Results result = SchedulingAlgorithm.Run(runtime, processVector);
    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
      out.println("Scheduling Type: " + result.schedulingType);
      out.println("Scheduling Name: " + result.schedulingName);
      out.println("Simulation Run Time: " + result.compuTime);
      out.println("Process #\trelease Time\texecution time\tdeadline\ttime period\tcpu time");
      for (i = 0; i < processVector.size(); i++) {
        Process process = processVector.elementAt(i);
        if (i < 100) { out.print("\t\t"); } else { out.print("\t"); }
        out.print(i);
        if (process.releaseTime < 100) { out.print("\t\t"); } else { out.print("\t"); }
        out.print(process.releaseTime);
        if (process.executionTime < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
        out.print(process.executionTime);
        if (process.deadline < 100) { out.print(" (ms)\t\t\t"); } else { out.print(" (ms)\t\t"); }
        out.print(process.deadline);
        if (process.timePeriod < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
        out.print(process.timePeriod);
        out.print(" (ms)\t\t");
        out.print(process.cpuTime);
        out.println(" (ms)\t\t");
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
  System.out.println("Completed.");
  }
}

