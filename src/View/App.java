package View;
import java.awt.*;
import java.util.Scanner;
import Process.Process;
import Scheduling.SchedulingAlgorithms.SchedulingAlgorithm;
import Scheduling.SchedulingAlgorithms.SchedulingFactory;
import javax.swing.*;

public class App {
    private SchedulingFactory schedulingFactory = null;
    private SchedulingAlgorithm schedulingAlgorithm = null;
    public void run(){
        int numOfProcesses = getNumberOfProcesses();
        int quantum = getQuantum();
        int contextSwitchTime = getContextSwitchTime();
        schedulingFactory = new SchedulingFactory(contextSwitchTime, quantum);
        String schedulingAlgorithmName = getSchedulingAlgorithmName();
        schedulingAlgorithm = schedulingFactory.getSchedulingAlgorithm(schedulingAlgorithmName);

        for (int i = 0; i < numOfProcesses; i++){
            schedulingAlgorithm.addProcess(getProcessData());
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("OS assignment 3");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new GanttChartPanelSJF(schedulingAlgorithm.scheduleProcesses(), schedulingAlgorithm.getProcessesWaitTime(), schedulingAlgorithm.getProcessTurnaroundTime()), BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
    }

    private int getNumberOfProcesses(){
        System.out.print("Enter the number of processes: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    private int getQuantum() {
        System.out.print("Enter the round robin time quantum: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    private int getContextSwitchTime(){
        System.out.print("Enter the ContextSwitchTime: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }
    private Process getProcessData(){
        System.out.print("Enter ProcessName: ");
        String name = new Scanner(System.in).nextLine();

        Color color = getRandomColor();

        System.out.print("Enter ProcessArrivalTime: ");
        int arrivalTime = Integer.parseInt(new Scanner(System.in).nextLine());

        System.out.print("Enter ProcessBurstTime: ");
        int burstTime = Integer.parseInt(new Scanner(System.in).nextLine());

        System.out.print("Enter ProcessPriorityNumber: ");
        int priorityNumber = Integer.parseInt(new Scanner(System.in).nextLine());

        return new Process(name,color,arrivalTime,burstTime,priorityNumber);
    }

    private String getSchedulingAlgorithmName(){
        String[] schedulingAlgorithmsNames = schedulingFactory.getSchedulingAlgorithmsNames();
        for(int i = 0; i < schedulingAlgorithmsNames.length; i++){
            System.out.println((i + 1) + "- " + schedulingAlgorithmsNames[i]);
        }
        System.out.print(">> ");
        int choose = Integer.parseInt(new Scanner(System.in).nextLine());
        return schedulingAlgorithmsNames[choose - 1];
    }

    private Color getRandomColor() {
        float r = (float) Math.random();
        float g = (float) Math.random();
        float b = (float) Math.random();
        return new Color(r, g, b);
    }
}
