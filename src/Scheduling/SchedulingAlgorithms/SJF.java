package Scheduling.SchedulingAlgorithms;

import Scheduling.ExecutionBurst;
import Process.Process;
import java.util.*;

public class SJF extends SchedulingAlgorithm{
    public SJF(int contextSwitchTime){
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
                return Integer.compare(p1.burstTime, p2.burstTime);
            }
        });
        int completed = 0;
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
            currentTime = Math.max(currentTime,process.arrivalTime) + process.burstTime;
            process.burstTime = 0;
            completed++;
            if(completed < processes.size()){
                currentTime += contextSwitchTime;
            }
            i++;
        }
        isScheduled = true;
        return  executionBursts;
    }
}