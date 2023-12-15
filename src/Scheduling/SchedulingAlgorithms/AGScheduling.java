package Scheduling.SchedulingAlgorithms;
import Scheduling.ExecutionBurst;
import java.util.*;
import Process.Process;
import Process.AGProcess;

public class AGScheduling extends SchedulingAlgorithm{
    List<AGProcess> processes;
    int[] waitTime;
    int quantum ;
    Queue<AGProcess> queue;

    public AGScheduling(int quantum, int contextSwitchTime){
        super();
        super.contextSwitchTime = contextSwitchTime;
        this.processes = new ArrayList<>();
        this.quantum = quantum;
        this.queue = new ArrayDeque<>();
    }

    @Override
    public void addProcess(Process process){
        int agFactor = calculateAGFactor(process);
        processes.add(new AGProcess(process,quantum,agFactor));
    }
    public List<ExecutionBurst> scheduleProcesses(){
        if(isScheduled){
            return this.executionBursts;
        }
        sortProcesses();
        int currentTime = 0;
        int completed = 0 ;
        waitTime = new int[processes.size()];
        AGProcess activeAGProcess = getLowestArrivedAGProcess(currentTime);
        while (completed < processes.size()){
            if(activeAGProcess == null){ // CPU IDLE
                currentTime++;
                activeAGProcess = getLowestArrivedAGProcess(currentTime);
                continue;
            }
            int halfQuantum = (int) Math.ceil(activeAGProcess.quantum / 2.0);
            int start = currentTime;
            int oldQuantum = activeAGProcess.quantum;
            currentTime = executeQuantum(Math.min(halfQuantum,activeAGProcess.process.burstTime),currentTime,activeAGProcess);
            while (activeAGProcess.quantum > 0 && activeAGProcess.process.burstTime > 0  && isHighestPriorityProcess(currentTime,activeAGProcess)){
                currentTime = executeQuantum(1,currentTime,activeAGProcess);
            }
            if(activeAGProcess.process.burstTime == 0 ){
                completed++;
                activeAGProcess.quantum = 0 ;
            }
            if(activeAGProcess.quantum == 0 ){
                int waitTime = getProcessBurstWaitTime(activeAGProcess.process.name);
                executionBursts.add(new ExecutionBurst(activeAGProcess.process,start,currentTime,waitTime));
                if(activeAGProcess.process.burstTime != 0 ){
                    activeAGProcess.quantum = oldQuantum + calculateMeanQuantum();
                }
                if(!queue.isEmpty()){
                    AGProcess oldProcess = activeAGProcess;
                    activeAGProcess = queue.peek();
                    currentTime = applyContextSwitchTime(currentTime,oldProcess,activeAGProcess);
                    queue.poll();
                }else{
                    AGProcess oldProcess = activeAGProcess;
                    activeAGProcess = getLowestArrivedAGProcess(currentTime);
                    currentTime = applyContextSwitchTime(currentTime,oldProcess,activeAGProcess);
                }
            }else{
                int waitTime = getProcessBurstWaitTime(activeAGProcess.process.name);
                executionBursts.add(new ExecutionBurst(activeAGProcess.process,start,currentTime,waitTime));
                activeAGProcess.quantum += oldQuantum;
                queue.add(activeAGProcess);

                AGProcess oldProcess = activeAGProcess;
                activeAGProcess = getLowestArrivedAGProcess(currentTime);
                currentTime = applyContextSwitchTime(currentTime,oldProcess,activeAGProcess);
            }
        }
        isScheduled = true;
        return executionBursts;
    }
    private int applyContextSwitchTime(int currentTime,AGProcess currentRunningProcess,AGProcess nextProcess){
        if(nextProcess == null){
            return currentTime;
        }
        if(Objects.equals(currentRunningProcess.process.name, nextProcess.process.name)){
            return currentTime;
        }
        for(int t = 0; t < contextSwitchTime; t++){
            increaseProcessesWaitTime(currentTime, null);
            currentTime++;
        }
        return currentTime;
    }
    private void sortProcesses(){
        processes.sort((p1, p2) -> {
            if (p1.process.arrivalTime != p2.process.arrivalTime) {
                return Integer.compare(p1.process.arrivalTime, p2.process.arrivalTime);
            } else {
                return Integer.compare(p1.agFactor,p2.agFactor);
            }
        });
    }
    private int executeQuantum(int quantum, int currentTime, AGProcess agProcess){
        for(int i = 0 ;i<quantum;i++){
            agProcess.quantum--;
            agProcess.process.burstTime--;
            increaseProcessesWaitTime(currentTime,agProcess);
            currentTime++;
        }
        return currentTime;
    }
    private AGProcess getLowestArrivedAGProcess(int currentTime){
        AGProcess process = null;
        for (AGProcess agProcess : processes) {
            if (agProcess.process.arrivalTime <= currentTime && agProcess.process.burstTime > 0 ) {
                if (process == null) {
                    process = agProcess;
                } else if (process.agFactor > agProcess.agFactor) {
                    process = agProcess;
                }
            }
        }
        if(process != null){
            queue.remove(process);
        }
        return process;
    }
    private boolean isHighestPriorityProcess(int currentTime, AGProcess agProcess){
        AGProcess highestPriorityProcess = getLowestArrivedAGProcess(currentTime);
        return Objects.equals(highestPriorityProcess.process.name, agProcess.process.name);
    }
    private void increaseProcessesWaitTime(int currentTime, AGProcess activeAGProcess){
        for(int i = 0; i < processes.size(); i++){
            if(processes.get(i).process.arrivalTime <= currentTime && activeAGProcess == null){
                waitTime[i]++;
            }
            else if(processes.get(i).process.arrivalTime <= currentTime && !Objects.equals(processes.get(i).process.name, activeAGProcess.process.name)){
                waitTime[i]++;
            }
        }
    }
    private int getProcessBurstWaitTime(String processName){
        for(int i= 0  ;i<processes.size();i++){
            if(Objects.equals(processes.get(i).process.name, processName)){
                int timeWaited = waitTime[i];
                waitTime[i] = 0 ;
                return timeWaited;
            }
        }
        return 0;
    }
    private int calculateMeanQuantum(){
        int ret = 0;
        for (AGProcess process : processes) {
            ret += process.quantum;
        }
        ret /= processes.size();
        return (int) Math.ceil(ret / 10.0);
    }
    private int calculateAGFactor(Process process){
        Random random = new Random();
        int randomFactor = random.nextInt(21);
        int agFactor = process.arrivalTime + process.burstTime;
        if(randomFactor < 10){
            agFactor += randomFactor;
        }else if(randomFactor > 10){
            agFactor += 10;
        }else{
            agFactor += process.priorityNumber;
        }
//        if(Objects.equals(process.name, "p1")){
//            agFactor = 20;
//        }
//        else if(Objects.equals(process.name, "p2")){
//            agFactor = 17;
//        }
//        else if(Objects.equals(process.name, "p3")){
//            agFactor = 16;
//        }
//        else{
//            agFactor = 43;
//        }
        return agFactor;
    }
}
