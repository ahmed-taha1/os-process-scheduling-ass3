import Scheduling.ExecutionBurst;
import Scheduling.SchedulingAlgorithms.*;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import Process.Process;
import View.App;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.run();

        // testing
//        Testing.testPriorityScheduling();
//        Testing.testSRTF();
//        Testing.testAGScheduling();
//        Testing.generalTest(new AGScheduling(4, 2));
    }
}