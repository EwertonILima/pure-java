package stage1;

import java.io.IO;

public class ExampleDeadlock {
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (resourceA) {
                IO.println("Thread 1: locked resource A");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }

                synchronized (resourceB) {
                    IO.println("Thread 1: locked resource B");

                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (resourceB) {
                IO.println("Thread 2: locked resource B");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }

                synchronized (resourceA) {
                    IO.println("Thread 2: locked resource A");

                }
            }
        });

        t1.start();
        t2.start();
    }
}
