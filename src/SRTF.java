import java.util.*;

public class SRTF {
    private final List<Process> processes ;
    private final int contextSwitchTime ;
    private boolean isScheduled ;
    private final List<ExecutionBurst> executionBursts;
    public SRTF(int contextSwitchTime){
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
//        processes.sort(Comparator.comparingInt(process -> process.burstTime));
        int completed = 0 ;
        int currentTime = 0;
        int currentWorkingProcessIndex = -1;
        int[] processesStartTime = new int[processes.size()];
        for (int i = 0; i < processes.size(); i++) {
            processesStartTime[i] = -1;
        }

        int[] processesBurstTime = getProcessesBurstTime();
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
                currentWorkingProcessIndex = minArrivedBurstTimeProcessIndex;
                currentTime += contextSwitchTime;

                // first time in
                if(processesStartTime[currentWorkingProcessIndex] == -1)
                    processesStartTime[currentWorkingProcessIndex] = currentTime;
            }

            processes.get(currentWorkingProcessIndex).burstTime--;
            currentTime++;

            // if the process finished swap it out
            if(processes.get(currentWorkingProcessIndex).burstTime == 0){
                int start = processesStartTime[currentWorkingProcessIndex];
                int end = currentTime;
                int burstTime = processesBurstTime[currentWorkingProcessIndex];
                int arrivalTime = processes.get(currentWorkingProcessIndex).arrivalTime;
                executionBursts.add(new ExecutionBurst(processes.get(currentWorkingProcessIndex),start,end, end - (arrivalTime + burstTime)));
                currentWorkingProcessIndex = -1;
                completed++;
            }

        }
        isScheduled = true;
        return  executionBursts;
    }

    private int[] getProcessesBurstTime() {
        int [] processesBurstTime = new int[processes.size()];
        for(int i = 0; i < processes.size(); i++){
            processesBurstTime[i] = processes.get(i).burstTime;
        }
        return processesBurstTime;
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
