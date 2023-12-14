import Scheduling.ExecutionBurst;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

import Process.Process;
import Scheduling.SchedulingAlgorithms.SchedulingAlgorithm;

class GanttChartPanelSJF extends JPanel {
    private List<ExecutionBurst> executedProcesses;
    SchedulingAlgorithm schedulingAlgorithm;

    public GanttChartPanelSJF(List<ExecutionBurst> executedProcesses, SchedulingAlgorithm schedulingAlgorithm) {
        this.executedProcesses = executedProcesses;
        this.schedulingAlgorithm = schedulingAlgorithm;
        setPreferredSize(new Dimension(1500, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int currentTime = 0;
        int yOffset = 50; // Y offset for the Gantt chart
        int height = 30; // Height of the Gantt chart bar
        int tickSpacing = 20;

        // Draw X-axis
        g.drawLine(0, yOffset + height + 20, getWidth(), yOffset + height + 20);

        // Draw Y-axis
        g.drawLine(0, yOffset - 10, 0, getHeight());

        ExecutionBurst last = null;
        for (ExecutionBurst burst : executedProcesses) {
            if (last != null) {
                int switchTime = burst.start - last.end;
                g.setColor(Color.white);
                g.fillRect(currentTime * 20, yOffset, switchTime * 20, height);
                g.setColor(Color.BLACK);
                g.drawString("", currentTime * 20, yOffset - 5);
                currentTime += switchTime;
            }
            g.setColor(Color.decode(burst.executedProcess.color));
            g.fillRect(currentTime * 20, yOffset, burst.getBurstTime() * 20, height);
            g.setColor(Color.BLACK);
            g.drawString(burst.executedProcess.name, currentTime * 20, yOffset - 5);
            // Display wait time and turnaround time
//            g.drawString(burst.executedProcess.name, currentTime * 20, yOffset + height + 60);
//            g.drawString("TAT: " + (burst.end - burst.executedProcess.arrivalTime), currentTime * 20, yOffset + height + 80);
//            g.drawString("WT: " + burst.waitTime, currentTime * 20, yOffset + height + 100);
            currentTime += burst.getBurstTime();
            last = burst;
        }

        Map<String, Integer> waitMap = schedulingAlgorithm.getProcessesWaitTime();
        Map<String, Integer> turnAroundTime = schedulingAlgorithm.getProcessTurnaroundTime();
        int totalWait = 0, totalTurnAroundTime = 0;
        int currentHeight = 150;
        for(var i : waitMap.keySet()){
            totalWait += waitMap.get(i);
            totalTurnAroundTime += turnAroundTime.get(i);
            g.drawString(i, 10, currentHeight);
            g.drawString("TAT: " + turnAroundTime.get(i), 10, currentHeight + 20);
            g.drawString("WT: " + waitMap.get(i), 10, currentHeight + 40);
            currentHeight += 100;
        }
        g.drawString("average wait time: " + totalWait / (double) waitMap.size(), 10, currentHeight + 20);
        g.drawString("average turn around time: " + totalTurnAroundTime / (double) waitMap.size(), 10, currentHeight + 40);

        // Draw number lines on X-axis
        for (int i = 0; i <= 100; i++) {
            g.drawLine(i * 20, yOffset + height + 15, i * 20, yOffset + height + 25);
            g.drawString(Integer.toString(i), i * 20 - 5, yOffset + height + 40);
        }

        // Draw number lines on Y-axis
        for (int i = yOffset; i <= yOffset + height; i += height) {
            g.drawLine(-10, i, 10, i);
            g.drawString(Integer.toString(i - yOffset), -25, i + 5);
        }
    }
}


public class Chart extends JPanel {
    private final List<ExecutionBurst>list;
    public Chart(List<ExecutionBurst>list){
        this.list = list;
        setPreferredSize(new Dimension(1000,1000));
    }
//    @Override
//    protected void paintComponent(Graphics graphics){
//        super.paintComponent(graphics);
//        int height = 30;
//        int taskSpacing = 10;
//        int y = 50;
//        int FACTOR = 5;
//        for (ExecutionBurst Burt : list) {
//            int xStart = Burt.start ;
//            int xEnd = Burt.end;
//            int width = (xEnd - xStart) * FACTOR;
//
//            graphics.setColor(Color.decode(Burt.executedProcess.color));
//            graphics.fillRect(xStart, y, width, height);
//
//            graphics.setColor(Color.BLACK);
//            graphics.drawString(Burt.executedProcess.name, xStart, y + height / 2);
//
//            y += height + taskSpacing ;
//        }
//
//        int maxX = list.stream().mapToInt(ExecutionBurst::getBurstTime).max().orElse(0);
//        int maxY = y + taskSpacing;
//
//        // Draw Timeline
//        graphics.setColor(Color.RED);
//        graphics.drawLine(0, y, maxX, y);
//
//        // Draw Timeline labels
//        int labelSpacing = 500;
//        for (int i = 0; i <= maxX; i += labelSpacing) {
//            graphics.drawString(String.valueOf(i), i, y + 20);
//        }
//
//    }
}
