package Scheduling.SchedulingAlgorithms;
import Scheduling.ExecutionBurst;
import Process.Process;
import java.util.List;


public class SRTF extends SchedulingAlgorithm{
    public SRTF(int contextSwitchTime){
        super();
        super.contextSwitchTime = contextSwitchTime;
    }

    @Override
    public List<ExecutionBurst>scheduleProcesses(){
        if(isScheduled){
            return executionBursts;
        }
        int completed = 0 ;
        int currentTime = 0;
        int currentWorkingProcessIndex = -1;
        int[] processesStartTime = new int[processes.size()];
        int[] processesWaitTime = new int[processes.size()];
        for (int i = 0; i < processes.size(); i++) {
            processesStartTime[i] = -1;
            processesWaitTime[i] = 0;
        }

        while (completed < processes.size()){
            // get the minimum arrived burst time process
            int minArrivedBurstTimeProcessIndex = -1;
            int minArrivedProcessBurstTime = Integer.MAX_VALUE;
            for(int i = 0; i < processes.size(); i++){
                if(processes.get(i).arrivalTime <= currentTime){
                    if(processes.get(i).burstTime < minArrivedProcessBurstTime && processes.get(i).burstTime > 0){
                        minArrivedProcessBurstTime = processes.get(i).burstTime;
                        minArrivedBurstTimeProcessIndex = i;
                    }
                }
            }
            // if no found at current time and no working process
            if(currentWorkingProcessIndex == -1 && minArrivedBurstTimeProcessIndex == -1){
                currentTime++;
                continue;
            }

            // if there is no process working or the found process burst time is less than the current process remaining time
            if(currentWorkingProcessIndex == -1 || processes.get(currentWorkingProcessIndex).burstTime > minArrivedProcessBurstTime){
                // add the last working process to the execution burst
                if(currentWorkingProcessIndex != -1){
                    calcAndStoreExecutionBurst(currentWorkingProcessIndex, currentTime, processesStartTime, processesWaitTime);
                    processesWaitTime[currentWorkingProcessIndex] = 0;
                }
                waitAllArrivedProcesses(processesWaitTime, currentWorkingProcessIndex, contextSwitchTime, currentTime);
                currentWorkingProcessIndex = minArrivedBurstTimeProcessIndex;
                currentTime += contextSwitchTime;
                processesStartTime[currentWorkingProcessIndex] = currentTime;
            }

            processes.get(currentWorkingProcessIndex).burstTime--;
            waitAllArrivedProcesses(processesWaitTime, currentWorkingProcessIndex, 1, currentTime);
            currentTime++;


            // if the process finished swap it out
            if(processes.get(currentWorkingProcessIndex).burstTime == 0){
                calcAndStoreExecutionBurst(currentWorkingProcessIndex, currentTime, processesStartTime, processesWaitTime);
                processesWaitTime[currentWorkingProcessIndex] = 0;
                currentWorkingProcessIndex = -1;
                completed++;
            }
        }

        isScheduled = true;
        return  executionBursts;
    }

    private void waitAllArrivedProcesses(int[] processesWaitTime, int currentWorkingProcessIndex, int time, int currentTime) {
        for(int i = 0; i < processesWaitTime.length; i++){
            // check if it arrived and not working now
            if(processes.get(i).arrivalTime <= currentTime && currentWorkingProcessIndex != i) {
                processesWaitTime[i] += time;
            }
        }
    }

    private void calcAndStoreExecutionBurst(int processIndex, int currentTime, int[] processesStartTime, int[] processesWaitTime){
        int start = processesStartTime[processIndex];
        int waitTime = processesWaitTime[processIndex];
        int end = currentTime;
        Process process = processes.get(processIndex);
        executionBursts.add(new ExecutionBurst(process, start, end, waitTime));
    }
}