import java.util.concurrent.locks.*;

public class ReadersWriters {

    private static int sharedData = 0;

    private static final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final Lock readLock = rwLock.readLock();
    private static final Lock writeLock = rwLock.writeLock();

    static class Reader extends Thread {
        private final int id;

        public Reader(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                readLock.lock();
                try {
                    System.out.println("[Reader " + id + "] Reading data: " + sharedData);
                    Thread.sleep((int)(Math.random() * 1000));
                } finally {
                    readLock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("[Reader " + id + "] Interrupted.");
            }
        }
    }

    static class Writer extends Thread {
        private final int id;

        public Writer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("[Writer " + id + "] Waiting to write...");
                writeLock.lock();
                try {
                    sharedData++;
                    System.out.println("[Writer " + id + "] Writing data: " + sharedData);
                    Thread.sleep((int)(Math.random() * 1000));
                } finally {
                    writeLock.unlock();
                    System.out.println("[Writer " + id + "] Finished writing.");
                }
            } catch (InterruptedException e) {
                System.out.println("[Writer " + id + "] Interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            if (i % 3 == 0) {
                threads[i] = new Writer(i / 3);
            } else {
                threads[i] = new Reader(i);
            }
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All readers and writers have finished.");
    }
}
