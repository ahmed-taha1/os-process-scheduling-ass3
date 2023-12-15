package Process;
import Process.Process;

public class AGProcess {
    public Process process;
    public int quantum;
    public int agFactor;
    public AGProcess(Process process,int quantum,int agFactor){
        this.process = process;
        this.quantum = quantum;
        this.agFactor = agFactor;
    }
}
