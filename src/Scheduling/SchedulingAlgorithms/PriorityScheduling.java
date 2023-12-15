package Scheduling.SchedulingAlgorithms;

import Scheduling.ExecutionBurst;
import Process.Process;
import java.util.*;
public class PriorityScheduling extends SchedulingAlgorithm{
    public PriorityScheduling(int contextSwitchTime){
        super();
        super.contextSwitchTime = contextSwitchTime;
    }

    @Override
    public void addProcess(Process process) {
        this.processes.add(process);
    }

    @Override
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
            Process activeProcess = processes.get(i);
            int start = Math.max(currentTime,activeProcess.arrivalTime);
            int end = start + activeProcess.burstTime;
            int waitTime = 0;
            if(activeProcess.arrivalTime < currentTime){
                waitTime = currentTime - activeProcess.arrivalTime;
            }
            starvationHandler(currentTime,activeProcess.burstTime + contextSwitchTime);
            executionBursts.add(new ExecutionBurst(activeProcess,start,end,waitTime));
            currentTime = Math.max(currentTime,activeProcess.arrivalTime) + activeProcess.burstTime ;
            activeProcess.burstTime = 0 ;
            completed++;
            if(completed < processes.size()){
                currentTime += contextSwitchTime;
            }
            i++;
        }
        isScheduled = true;
        return  executionBursts;
    }

    // APPLY AGING FOR SOLVING STARVATION PROBLEM
    private void starvationHandler(int currentTime,int burstTime){
        for(int j = 0 ;j < burstTime ;j++){
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime) {
                    process.priorityNumber++;
                }
            }
            currentTime++;
        }
    }
}
