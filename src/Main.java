import Scheduling.ExecutionBurst;
import Scheduling.SchedulingAlgorithms.PriorityScheduling;
import Scheduling.SchedulingAlgorithms.SJF;
import Scheduling.SchedulingAlgorithms.SRTF;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import Process.Process;

public class Main {
    public static void main(String[] args) {
        //// GUI

//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Gantt Chart Example");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.getContentPane().add(new Chart(list), BorderLayout.CENTER);
//            frame.pack();
//            frame.setVisible(true);
//        });

        /// CONSOLE

//        int processesCount = getNumberOfProcesses();
//        int contextSwitchTime = getContextSwitchTime();
    /* for (int i = 0 ; i<processesCount;i++){
            System.out.println(">>>>>> Process.Process ("+(i+1)+") Data");
            Process.Process process = getProcessData();
            sjfNonPreemptive.addProcess(process);
            System.out.println("==============================");
        }*/

//        testPriorityScheduling();
        testSRTF();
    }
    public static void testPriorityScheduling(){
        PriorityScheduling priorityScheduling = new PriorityScheduling(5);
        priorityScheduling.addProcess(new Process("p1","#FF0000",0,10,1));
        priorityScheduling.addProcess(new Process("p2","#0000FF",0,20,2));
        priorityScheduling.addProcess(new Process("p3","#00FF00",0,5,3));
        priorityScheduling.addProcess(new Process("p4","#FFFF00",0,15,4));

        List<ExecutionBurst> executionBursts = priorityScheduling.scheduleProcesses();

        for (ExecutionBurst executionBurst : executionBursts) {
            System.out.println(executionBurst.executedProcess.name + "=> " + executionBurst.start+ "=>"+ executionBurst.end + " ?? "+ executionBurst.waitTime);
        }
        System.out.println("--------------- WaitTime ------------------------");
        Map<String,Integer> waitList = priorityScheduling.getProcessWaitTime();
        for(var entry : waitList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
        System.out.println("---------------- TurnAround -----------------------");
        Map<String,Integer> turnAroundTList = priorityScheduling.getProcessTurnaroundTime();
        for(var entry : turnAroundTList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
    }
    public static void testSRJF(){

    }
    public static void testSJFNonPreemptive(){
        SJF sjf = new SJF(5);
        sjf.addProcess(new Process("p1","#FF0000",0,10,1));
        sjf.addProcess(new Process("p2","#0000FF",0,20,2));
        sjf.addProcess(new Process("p3","#00FF00",0,5,3));
        sjf.addProcess(new Process("p4","#FFFF00",0,15,4));

        List<ExecutionBurst> executionBursts = sjf.scheduleProcesses();

        for (ExecutionBurst executionBurst : executionBursts) {
            System.out.println(executionBurst.executedProcess.name + "=> " + executionBurst.start+ "=>"+ executionBurst.end + " ?? "+ executionBurst.waitTime);
        }
        System.out.println("--------------- WaitTime ------------------------");
        Map<String,Integer> waitList = sjf.getProcessWaitTime();
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
        SRTF srtf = new SRTF(2);
        srtf.addProcess(new Process("p1","#FF0000",1,10,1));
        srtf.addProcess(new Process("p2","#0000FF",3,20,2));
        srtf.addProcess(new Process("p3","#00FF00",1,5,3));
        srtf.addProcess(new Process("p4","#FFFF00",3,1,4));

        List<ExecutionBurst> executionBursts = srtf.scheduleProcesses();

        for (ExecutionBurst executionBurst : executionBursts) {
            System.out.println(executionBurst.executedProcess.name + "=> " + executionBurst.start+ "=>"+ executionBurst.end + " ?? "+ executionBurst.waitTime);
        }
        System.out.println("--------------- WaitTime ------------------------");
        Map<String,Integer> waitList = srtf.getProcessWaitTime();
        for(var entry : waitList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
        System.out.println("---------------- TurnAround -----------------------");
        Map<String,Integer> turnAroundTList = srtf.getProcessTurnaroundTime();
        for(var entry : turnAroundTList.entrySet()){
            System.out.println(entry.getKey() + " => " +entry.getValue());
        }
    }

    public static int getNumberOfProcesses(){
        System.out.print("Enter the number of processes:");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
    public static int getContextSwitchTime(){
        System.out.print("Enter the ContextSwitchTime:");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
    public static Process getProcessData(){
        System.out.print("Enter ProcessName:");
        String name = new Scanner(System.in).nextLine();

        System.out.print("Enter Process.Process Color:");
        String color = new Scanner(System.in).nextLine();

        System.out.print("Enter ProcessArrivalTime:");
        int arrivalTime = Integer.parseInt(new Scanner(System.in).nextLine());

        System.out.print("Enter ProcessBurstTime:");
        int burstTime = Integer.parseInt(new Scanner(System.in).nextLine());

        System.out.print("Enter ProcessPriorityNumber:");
        int priorityNumber = Integer.parseInt(new Scanner(System.in).nextLine());

        return new Process(name,color,arrivalTime,burstTime,priorityNumber);
    }
}