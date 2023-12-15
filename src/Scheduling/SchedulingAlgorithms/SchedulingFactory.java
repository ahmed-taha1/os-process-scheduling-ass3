package Scheduling.SchedulingAlgorithms;

import java.util.HashMap;
import java.util.Map;

public class SchedulingFactory {
    public Map<String, SchedulingAlgorithm> schedulingAlgorithmsMap;
    public SchedulingFactory(int contextSwitch, int quantum){
        schedulingAlgorithmsMap = new HashMap<>();
        schedulingAlgorithmsMap.put("SRTF", new SRTF(contextSwitch));
        schedulingAlgorithmsMap.put("SJF", new SJF(contextSwitch));
        schedulingAlgorithmsMap.put("Priority Scheduling", new PriorityScheduling(contextSwitch));
        schedulingAlgorithmsMap.put("AG", new AGScheduling(quantum, contextSwitch));
    }

    public String[] getSchedulingAlgorithmsNames() {
        return schedulingAlgorithmsMap.keySet().toArray(new String[0]);
    }

    public SchedulingAlgorithm getSchedulingAlgorithm(String algorithmName){
        return schedulingAlgorithmsMap.get(algorithmName);
    }
}
