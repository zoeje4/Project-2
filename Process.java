public class Process extends Thread {
    private int pid;
    private int arrivalTime;
    private int burstTime;
    private int priority;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
  p      this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    @Override
    public void run() {
        try {
            System.out.println("[Process " + pid + "] Arrived at time: " + arrivalTime);
            System.out.println("[Process " + pid + "] Running for " + burstTime + " seconds...");
            Thread.sleep(burstTime * 1000);  // simulate burst time
            System.out.println("[Process " + pid + "] Finished execution.");
        } catch (InterruptedException e) {
            System.err.println("Process " + pid + " interrupted.");
        }
    }
}
