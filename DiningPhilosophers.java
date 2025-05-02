import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;
    private static final ReentrantLock[] forks = new ReentrantLock[NUM_PHILOSOPHERS];

    static class Philosopher extends Thread {
        private final int id;

        public Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            int leftFork = id;
            int rightFork = (id + 1) % NUM_PHILOSOPHERS;

            int firstFork = Math.min(leftFork, rightFork);
            int secondFork = Math.max(leftFork, rightFork);

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("[Philosopher " + id + "] Thinking...");
                    Thread.sleep((int)(Math.random() * 1000)); // Thinking

                    System.out.println("[Philosopher " + id + "] Waiting for forks...");
                    
                    System.out.println("[Philosopher " + id + "] Waiting for fork " + firstFork);
                    forks[firstFork].lock();
                    System.out.println("[Philosopher " + id + "] Picked up fork " + firstFork);

                    System.out.println("[Philosopher " + id + "] Waiting for fork " + secondFork);
                    forks[secondFork].lock();
                    System.out.println("[Philosopher " + id + "] Picked up fork " + secondFork);

                    System.out.println("[Philosopher " + id + "] Picked up fork " + firstFork + " and " + secondFork);
                    System.out.println("[Philosopher " + id + "] Eating...");
                    Thread.sleep((int)(Math.random() * 1000)); // Eating

                    forks[secondFork].unlock();
                    forks[firstFork].unlock();
                    System.out.println("[Philosopher " + id + "] Released forks.");
                }
            } catch (InterruptedException e) {
                System.out.println("[Philosopher " + id + "] Interrupted and exiting.");
            }
        }
    }

    public static void main(String[] args) {
        // Initialize forks
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].start();
        }

        // Let them run for 10 seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupt all threads
        for (Philosopher p : philosophers) {
            p.interrupt();
        }

        // Wait for all to finish
        for (Philosopher p : philosophers) {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Dining philosophers simulation ended.");
    }
}
