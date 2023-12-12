package Scheduling.SchedulingAlgorithms;

import Scheduling.ExecutionBurst;
import Process.Process;
import java.util.*;
public class PriorityScheduling {
    private final List<Process> processes ;
    private final int contextSwitchTime ;
    private boolean isScheduled ;
    private final List<ExecutionBurst> executionBursts;
    public PriorityScheduling(int contextSwitchTime){
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
        processes.sort((p1, p2) -> {
            if (p1.arrivalTime != p2.arrivalTime) {
                return Integer.compare(p1.arrivalTime, p2.arrivalTime);
            } else {
                return Integer.compare(-p1.priorityNumber,-p2.priorityNumber);
            }
        });
        int completed = 0 ;
        int i = 0 ;
        int currentTime = 0 ;
        while (completed < processes.size()){
            Process process = processes.get(i);
            int start = Math.max(currentTime,process.arrivalTime);
            int end = start + process.burstTime;
            int waitTime = 0;
            if(process.arrivalTime < currentTime){
                waitTime = currentTime - process.arrivalTime;
            }
            executionBursts.add(new ExecutionBurst(process,start,end,waitTime));
            currentTime = Math.max(currentTime,process.arrivalTime) + process.burstTime ;
            process.burstTime = 0 ;
            completed++;
            if(completed < processes.size()){
                currentTime += contextSwitchTime;
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
