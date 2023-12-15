import Scheduling.ExecutionBurst;
import Scheduling.SchedulingAlgorithms.*;
import View.GanttChartPanelSJF;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import Process.Process;
public class Testing {
    public static void generalTest(SchedulingAlgorithm schedulingAlgorithm){
        schedulingAlgorithm.addProcess(new Process("p1",Color.green,0,17,4));
        schedulingAlgorithm.addProcess(new Process("p2",Color.blue,3,6,9));
        schedulingAlgorithm.addProcess(new Process("p3",Color.red,4,10,2));
        schedulingAlgorithm.addProcess(new Process("p4",Color.CYAN,29,4,8));

        List<ExecutionBurst> executionBursts = schedulingAlgorithm.scheduleProcesses();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gantt Chart Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new GanttChartPanelSJF(executionBursts, schedulingAlgorithm.getProcessesWaitTime(), schedulingAlgorithm.getProcessTurnaroundTime()), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
        for (ExecutionBurst executionBurst : executionBursts) {
            System.out.println(executionBurst.executedProcess.name + "=> " + executionBurst.start+ "=>"+ executionBurst.end + " ?? "+ executionBurst.waitTime);
        }
        System.out.println("--------------- WaitTime ------------------------");
        Map<String,Integer> waitList = schedulingAlgorithm.getProcessesWaitTime();
        for(var entry : waitList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
        System.out.println("---------------- TurnAround -----------------------");
        Map<String,Integer> turnAroundTList = schedulingAlgorithm.getProcessTurnaroundTime();
        for(var entry : turnAroundTList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
    }


    public static void testPriorityScheduling(){
        PriorityScheduling priorityScheduling = new PriorityScheduling(5);
        priorityScheduling.addProcess(new Process("p1", Color.ORANGE,0,10,1));
        priorityScheduling.addProcess(new Process("p2",Color.green,0,20,2));
        priorityScheduling.addProcess(new Process("p3",Color.red,0,5,3));
        priorityScheduling.addProcess(new Process("p4",Color.CYAN,0,15,4));

        java.util.List<ExecutionBurst> executionBursts = priorityScheduling.scheduleProcesses();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gantt Chart Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new GanttChartPanelSJF(executionBursts, priorityScheduling.getProcessesWaitTime(), priorityScheduling.getProcessTurnaroundTime()), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
        for (ExecutionBurst executionBurst : executionBursts) {
            System.out.println(executionBurst.executedProcess.name + "=> " + executionBurst.start+ "=>"+ executionBurst.end + " ?? "+ executionBurst.waitTime);
        }
        System.out.println("--------------- WaitTime ------------------------");
        Map<String,Integer> waitList = priorityScheduling.getProcessesWaitTime();
        for(var entry : waitList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
        System.out.println("---------------- TurnAround -----------------------");
        Map<String,Integer> turnAroundTList = priorityScheduling.getProcessTurnaroundTime();
        for(var entry : turnAroundTList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
    }
    public static void testSJF(){
        SJF sjf = new SJF(5);
        sjf.addProcess(new Process("p1",Color.ORANGE,0,10,1));
        sjf.addProcess(new Process("p2",Color.red,0,20,2));
        sjf.addProcess(new Process("p3",Color.green,0,5,3));
        sjf.addProcess(new Process("p4",Color.CYAN,0,15,4));

        java.util.List<ExecutionBurst> executionBursts = sjf.scheduleProcesses();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gantt Chart Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new GanttChartPanelSJF(executionBursts, sjf.getProcessesWaitTime(), sjf.getProcessTurnaroundTime()), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
        for (ExecutionBurst executionBurst : executionBursts) {
            System.out.println(executionBurst.executedProcess.name + "=> " + executionBurst.start+ "=>"+ executionBurst.end + " ?? "+ executionBurst.waitTime);
        }
        System.out.println("--------------- WaitTime ------------------------");
        Map<String,Integer> waitList = sjf.getProcessesWaitTime();
        for(var entry : waitList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
        System.out.println("---------------- TurnAround -----------------------");
        Map<String,Integer> turnAroundTList = sjf.getProcessTurnaroundTime();
        for(var entry : turnAroundTList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
    }

    public static void testSRTF(){
        SchedulingAlgorithm sjf = new SRTF(5);
        sjf.addProcess(new Process("p1",Color.yellow,4,10,1));
        sjf.addProcess(new Process("p2",Color.ORANGE,2,20,2));
        sjf.addProcess(new Process("p3",Color.cyan,1,5,3));
        sjf.addProcess(new Process("p4",Color.red,0,15,4));

        java.util.List<ExecutionBurst> executionBursts = sjf.scheduleProcesses();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gantt Chart Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new GanttChartPanelSJF(executionBursts, sjf.getProcessesWaitTime(), sjf.getProcessTurnaroundTime()), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
        for (ExecutionBurst executionBurst : executionBursts) {
            System.out.println(executionBurst.executedProcess.name + "=> " + executionBurst.start+ "=>"+ executionBurst.end + " ?? "+ executionBurst.waitTime);
        }
        System.out.println("--------------- WaitTime ------------------------");
        Map<String,Integer> waitList = sjf.getProcessesWaitTime();
        for(var entry : waitList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
        System.out.println("---------------- TurnAround -----------------------");
        Map<String,Integer> turnAroundTList = sjf.getProcessTurnaroundTime();
        for(var entry : turnAroundTList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
    }

    public static void testAGScheduling(){
        AGScheduling test = new AGScheduling(4, 2);
        test.addProcess(new Process("p1",Color.green,0,17,4));
        test.addProcess(new Process("p2",Color.blue,3,6,9));
        test.addProcess(new Process("p3",Color.red,4,10,2));
        test.addProcess(new Process("p4",Color.CYAN,29,4,8));

        List<ExecutionBurst> executionBursts = test.scheduleProcesses();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gantt Chart Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new GanttChartPanelSJF(executionBursts, test.getProcessesWaitTime(), test.getProcessTurnaroundTime()), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
        for (ExecutionBurst executionBurst : executionBursts) {
            System.out.println(executionBurst.executedProcess.name + "=> " + executionBurst.start+ "=>"+ executionBurst.end + " ?? "+ executionBurst.waitTime);
        }
        System.out.println("--------------- WaitTime ------------------------");
        Map<String,Integer> waitList = test.getProcessesWaitTime();
        for(var entry : waitList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
        System.out.println("---------------- TurnAround -----------------------");
        Map<String,Integer> turnAroundTList = test.getProcessTurnaroundTime();
        for(var entry : turnAroundTList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
    }
}
