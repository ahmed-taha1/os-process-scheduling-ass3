package View;

import Scheduling.ExecutionBurst;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

import Process.Process;
import Scheduling.SchedulingAlgorithms.SchedulingAlgorithm;

public class GanttChartPanel extends JPanel {
    private List<ExecutionBurst> executedProcesses;
    private Map<String, Integer> processesWaitTime;
    private Map<String, Integer> processesTurnAroundTime;

    public GanttChartPanel(List<ExecutionBurst> executedProcesses, Map<String, Integer> processesWaitTime, Map<String, Integer> processesTurnAroundTime) {
        this.executedProcesses = executedProcesses;
        this.processesWaitTime = processesWaitTime;
        this.processesTurnAroundTime = processesTurnAroundTime;
        setPreferredSize(new Dimension(1500, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int currentTime = 0;
        int yOffset = 50; // Y offset for the Gantt chart
        int height = 30; // Height of the Gantt chart bar

        // Draw X-axis
        g.drawLine(0, yOffset + height + 20, getWidth(), yOffset + height + 20);

        // Draw Y-axis
        g.drawLine(0, yOffset - 10, 0, getHeight());

        ExecutionBurst last = null; // for calc gaps between processes
        for (ExecutionBurst burst : executedProcesses) {
            if (last != null) {
                int switchTime = burst.start - last.end;
                g.setColor(Color.white);
                g.fillRect(currentTime * 20, yOffset, switchTime * 20, height);
                g.setColor(Color.BLACK);
                g.drawString("", currentTime * 20, yOffset - 5);
                currentTime += switchTime;
            }
            g.setColor(burst.executedProcess.color);
            g.fillRect(currentTime * 20, yOffset, burst.getBurstTime() * 20, height);
            g.setColor(Color.BLACK);
            g.drawString(burst.executedProcess.name, currentTime * 20, yOffset - 5);
            currentTime += burst.getBurstTime();
            last = burst;
        }


        // draw wait time for each process , avg wait time, turn around time for each process , avg turn around time
        int totalWait = 0, totalTurnAroundTime = 0;
        int currentHeight = 150;
        for(var i : processesWaitTime.keySet()){
            totalWait += processesWaitTime.get(i);
            totalTurnAroundTime += processesTurnAroundTime.get(i);
            g.drawString(i, 10, currentHeight);
            g.drawString("TAT: " + processesTurnAroundTime.get(i), 10, currentHeight + 20);
            g.drawString("WT: " + processesWaitTime.get(i), 10, currentHeight + 40);
            currentHeight += 100;
        }
        g.drawString("average wait time: " + totalWait / (double) processesWaitTime.size(), 10, currentHeight + 20);
        g.drawString("average turn around time: " + totalTurnAroundTime / (double) processesTurnAroundTime.size(), 10, currentHeight + 40);

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