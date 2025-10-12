package stage2;

import java.io.IO;

public class ExampleVisibilityBetweenThreads {
//    private static boolean running = true;
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() ->
        {
            IO.println("Secondary thread started");
            while (running) {
                // infinity loop
            }
            IO.println("Secondary thread ended");
        }).start();

        Thread.sleep(1000);
        running = false; // <- Theoretically, should stop the other thread
        IO.println("Main thread ended");
    }
}
