import Scheduling.ExecutionBurst;
import Scheduling.SchedulingAlgorithms.*;

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
        testAGScheduling();
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
        sjf.addProcess(new Process("p1","#FF0000",0,10,1));
        sjf.addProcess(new Process("p2","#0000FF",0,20,2));
        sjf.addProcess(new Process("p3","#00FF00",0,5,3));
        sjf.addProcess(new Process("p4","#FFFF00",0,15,4));

        List<ExecutionBurst> executionBursts = sjf.scheduleProcesses();

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
        test.addProcess(new Process("p1","#FF0000",0,17,4));
        test.addProcess(new Process("p2","#0000FF",3,6,9));
        test.addProcess(new Process("p3","#00FF00",4,10,2));
        test.addProcess(new Process("p4","#FFFF00",29,4,8));

        List<ExecutionBurst> executionBursts = test.scheduleProcesses();

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