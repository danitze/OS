public class Process {

  public int releaseTime;

  public int executionTime;

  public int deadline;

  public int timePeriod;

  public int cpuTime;

  public Process(int executionTime, int deadline, int timePeriod) {
    this.releaseTime = 0;
    this.executionTime = executionTime;
    this.deadline = deadline;
    this.timePeriod = timePeriod;
    this.cpuTime = 0;
  }

  @Override
  public String toString() {
    return "sProcess{" +
            "releaseTime=" + releaseTime +
            ", executionTime=" + executionTime +
            ", deadline=" + deadline +
            ", timePeriod=" + timePeriod +
            ", cpuTime=" + cpuTime +
            '}';
  }
}
