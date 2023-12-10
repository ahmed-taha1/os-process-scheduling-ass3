import java.util.*;

public class SJF {
    private final List<Process> processes ;
    private final int contextSwitchTime ;
    private boolean isScheduled ;
    private final List<ExecutionBurst> executionBursts;
    public SJF(int contextSwitchTime){
        this.processes = new ArrayList<>();
        this.contextSwitchTime = contextSwitchTime;
        this.executionBursts = new ArrayList<>();
        this.isScheduled = false;
    }
    public void addProcess(Process process){
        this.processes.add(process);
    }
    public List<ExecutionBurst>scheduleProcesses(){
        if(isScheduled){
            return executionBursts;
        }
        processes.sort(Comparator.comparingInt(process -> process.burstTime));
        int completed = 0 ;
        int i = 0 ;
        int totalTime = 0 ;
        while (completed < processes.size()){
            Process process = processes.get(i);
            executionBursts.add(new ExecutionBurst(process,totalTime,totalTime + process.burstTime,totalTime));
            totalTime += process.burstTime;
            process.burstTime = 0 ;
            completed++;
            if(completed < processes.size()){
                totalTime += contextSwitchTime;
            }
            i++;
        }
        isScheduled = true;
        return  executionBursts;
    }
    public Map<String,Integer> getProcessWaitTime(){
        List<ExecutionBurst> executionBursts = scheduleProcesses();
        Map<String,Integer>waitList = new HashMap<>();
        for (ExecutionBurst executionBurst : executionBursts) {
            Process process = executionBurst.executedProcess;
            int time = executionBurst.waitTime;
            if (waitList.get(process.name) == null) {
                waitList.put(process.name, time);
            } else {
                int newTime = waitList.get(process.name) + time;
                waitList.put(process.name, newTime);
            }
        }
        return waitList;
    }
    public Map<String, Integer> getProcessTurnaroundTime(){
        List<ExecutionBurst> executionBursts = scheduleProcesses();
        Map<String,Integer>turnAroundList = new HashMap<>();
        for (ExecutionBurst executionBurst : executionBursts) {
            Process process = executionBurst.executedProcess;
            int time = executionBurst.waitTime;
            int executionTime = executionBurst.end - executionBurst.start;
            if (turnAroundList.get(process.name) == null) {
                turnAroundList.put(process.name, time + executionTime);
            } else {
                int newTime = turnAroundList.get(process.name) + time + executionTime;
                turnAroundList.put(process.name, newTime);
            }
        }
        return turnAroundList;
    }
}
