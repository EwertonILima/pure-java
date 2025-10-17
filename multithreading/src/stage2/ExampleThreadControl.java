package stage2;

import java.io.IO;

public class ExampleThreadControl {
    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("Main-Thread");

        Runnable task1 = () -> {
            Thread.currentThread().setName("Worker-1");
            try {
                IO.println(Thread.currentThread().getName() + " starting...");
                Thread.sleep(5000); // heavy task
                IO.println(Thread.currentThread().getName() + " finished!");
            } catch (InterruptedException e) {
                IO.println(Thread.currentThread().getName() + "was interrupted!");
            }
        };

        Runnable task2 = () -> {
            Thread.currentThread().setName("Worker-2");
            try {
                IO.println(Thread.currentThread().getName() + " starting...");
                Thread.sleep(8000);
                IO.println(Thread.currentThread().getName() + " finished!");
            } catch (InterruptedException e) {
                IO.println(Thread.currentThread().getName() + " was interrupted!");
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        // Just wait for t1 to finish
        t1.join();// main awaits Worker-1
        IO.println("Main waited " + t1.getName());


        // Interrupt the other thread
        t2.interrupt();

        IO.println("Main finishing...");
    }
}
