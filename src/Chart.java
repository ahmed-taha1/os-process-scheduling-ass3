import Scheduling.ExecutionBurst;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Chart extends JPanel {
    private final List<ExecutionBurst>list;
    public Chart(List<ExecutionBurst>list){
        this.list = list;
        setPreferredSize(new Dimension(1000,1000));
    }
    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        int height = 30;
        int taskSpacing = 10;
        int y = 50;
        int FACTOR = 5;
        for (ExecutionBurst Burt : list) {
            int xStart = Burt.start ;
            int xEnd = Burt.end;
            int width = (xEnd - xStart) * FACTOR;

            graphics.setColor(Color.decode(Burt.executedProcess.color));
            graphics.fillRect(xStart, y, width, height);

            graphics.setColor(Color.BLACK);
            graphics.drawString(Burt.executedProcess.name, xStart, y + height / 2);

            y += height + taskSpacing ;
        }

        int maxX = list.stream().mapToInt(ExecutionBurst::getBurstTime).max().orElse(0);
        int maxY = y + taskSpacing;

        // Draw Timeline
        graphics.setColor(Color.RED);
        graphics.drawLine(0, y, maxX, y);

        // Draw Timeline labels
        int labelSpacing = 500;
        for (int i = 0; i <= maxX; i += labelSpacing) {
            graphics.drawString(String.valueOf(i), i, y + 20);
        }

    }
}
