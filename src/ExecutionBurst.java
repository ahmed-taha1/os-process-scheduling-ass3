
public class ExecutionBurst {
    public Process executedProcess;
    public int waitTime;
    public int start;
    public int end ;
    public ExecutionBurst(Process process , int start, int end, int waitTime){
        this.executedProcess = process;
        this.start = start;
        this.end = end;
        this.waitTime = waitTime;
    }
    public int getBurstTime(){
        return end - start;
    }
}
