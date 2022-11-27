import java.util.Objects;

public class sProcess {
  public int cputime;
  public int ioblocking;
  public int cpudone;
  public int ionext;
  public int numblocked;

  public int deadline;

  public sProcess (int cputime, int ioblocking, int cpudone, int ionext, int numblocked, int deadline) {
    this.cputime = cputime;
    this.ioblocking = ioblocking;
    this.cpudone = cpudone;
    this.ionext = ionext;
    this.numblocked = numblocked;
    this.deadline = deadline;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof sProcess)) return false;
    sProcess sProcess = (sProcess) o;
    return cputime == sProcess.cputime && ioblocking == sProcess.ioblocking && cpudone == sProcess.cpudone && ionext == sProcess.ionext && numblocked == sProcess.numblocked && deadline == sProcess.deadline;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cputime, ioblocking, cpudone, ionext, numblocked, deadline);
  }
}
