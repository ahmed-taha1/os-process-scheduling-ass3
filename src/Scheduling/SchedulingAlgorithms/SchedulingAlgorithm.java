package Scheduling.SchedulingAlgorithms;

import Scheduling.ExecutionBurst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Process.Process;

public abstract class SchedulingAlgorithm {
    protected final List<Process> processes ;
    protected int contextSwitchTime;
    protected boolean isScheduled;
    protected final List<ExecutionBurst> executionBursts;

    protected SchedulingAlgorithm() {
        this.processes = new ArrayList<>();
        this.isScheduled = false;
        this.executionBursts = new ArrayList<>();
    }

    public abstract List<ExecutionBurst>scheduleProcesses();

    public abstract void addProcess(Process process);

    public Map<String,Integer> getProcessesWaitTime(){
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
