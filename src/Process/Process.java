package Process;

public class Process {
    public final String name;
    public final String color;
    public final int arrivalTime;
    public int burstTime;
    public final int priorityNumber;
    public Process(String name,String color,int arrivalTime,int burstTime,int priorityNumber){
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priorityNumber = priorityNumber;
    }
}
