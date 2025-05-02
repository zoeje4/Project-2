import java.io.*;
import java.util.*;

public class ProcessSimulator {
    public static void main(String[] args) {
        List<Process> processList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("processes.txt"))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int pid = Integer.parseInt(parts[0]);
                int arrival = Integer.parseInt(parts[1]);
                int burst = Integer.parseInt(parts[2]);
                int priority = Integer.parseInt(parts[3]);

                Process p = new Process(pid, arrival, burst, priority);
                processList.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long startTime = System.currentTimeMillis();

        for (Process p : processList) {
            // delay based on arrival time
            long delay = p.arrivalTime * 1000L - (System.currentTimeMillis() - startTime);
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            p.start();
        }

        for (Process p : processList) {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All processes have completed execution.");
    }
}
