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
    public void addProcess(Process process) {
        this.processes.add(process);
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
        int[] quantum = new int[processes.size()];  // for solving starvation problem
        for (int i = 0; i < processes.size(); i++) {
            processesStartTime[i] = -1;
            processesWaitTime[i] = 0;
            quantum[i] = processes.get(i).burstTime;
        }

        while (completed < processes.size()){
            // get the minimum arrived burst time process
            int minArrivedQuantumProcessIndex = getMinArrivedIndex(quantum, currentTime);

            // if no found at current time and no working process
            if(currentWorkingProcessIndex == -1 && minArrivedQuantumProcessIndex == -1){
                currentTime++;
                continue;
            }

            // if there is no process working or the found process burst time is less than the current process remaining time
            if(currentWorkingProcessIndex == -1 || processes.get(currentWorkingProcessIndex).burstTime > processes.get(minArrivedQuantumProcessIndex).burstTime){
                // add the last working process to the execution burst
                if(currentWorkingProcessIndex != -1){
                    calcAndStoreExecutionBurst(currentWorkingProcessIndex, currentTime, processesStartTime, processesWaitTime);
                    processesWaitTime[currentWorkingProcessIndex] = 0;
                    waitAllArrivedProcesses(processesWaitTime, -1, contextSwitchTime, currentTime, quantum);
                    currentTime += contextSwitchTime;
                }
                currentWorkingProcessIndex = minArrivedQuantumProcessIndex;
                processesStartTime[currentWorkingProcessIndex] = currentTime;
            }

            processes.get(currentWorkingProcessIndex).burstTime--;
            waitAllArrivedProcesses(processesWaitTime, currentWorkingProcessIndex, 1, currentTime, quantum);
            currentTime++;


            // if the process finished swap it out
            if(processes.get(currentWorkingProcessIndex).burstTime == 0){
                calcAndStoreExecutionBurst(currentWorkingProcessIndex, currentTime, processesStartTime, processesWaitTime);
                processesWaitTime[currentWorkingProcessIndex] = 0;
                currentWorkingProcessIndex = -1;
                completed++;
                // if there is process ready to switch after the finished process
                if(getMinArrivedIndex(quantum, currentTime) != -1) {
                    waitAllArrivedProcesses(processesWaitTime, -1, contextSwitchTime, currentTime, quantum);
                    currentTime += contextSwitchTime;
                }
            }
        }
        isScheduled = true;
        return  executionBursts;
    }

    private void waitAllArrivedProcesses(int[] processesWaitTime, int currentWorkingProcessIndex, int time, int currentTime, int[] quantum) {
        int simTime = currentTime;
        for(int t = 0; t < time; t++) {
            for (int i = 0; i < processesWaitTime.length; i++) {
                // check if it arrived and not working now
                if (processes.get(i).arrivalTime <= simTime){
                    quantum[i]--;
                    if (currentWorkingProcessIndex != i && processes.get(i).burstTime != 0) {
                        processesWaitTime[i] += 1;
                    }
                }
            }
            simTime++;
        }
    }

    private void calcAndStoreExecutionBurst(int processIndex, int currentTime, int[] processesStartTime, int[] processesWaitTime){
        int start = processesStartTime[processIndex];
        int waitTime = processesWaitTime[processIndex];
        int end = currentTime;
        Process process = processes.get(processIndex);
        executionBursts.add(new ExecutionBurst(process, start, end, waitTime));
    }

    private int getMinArrivedIndex(int quantum[], int currentTime){
        int minArrivedQuantumProcessIndex = -1;
        int minArrivedProcessQuantum = Integer.MAX_VALUE;
        for(int i = 0; i < processes.size(); i++){
            if(processes.get(i).arrivalTime <= currentTime){
                if(quantum[i] < minArrivedProcessQuantum && processes.get(i).burstTime > 0){    // compare with the minimum quantum
                    minArrivedProcessQuantum = quantum[i];
                    minArrivedQuantumProcessIndex = i;
                }
            }
        }
        return minArrivedQuantumProcessIndex;
    }
}