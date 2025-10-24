package stage3;

import java.io.IO;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExampleScheduleExecutorService {
    public static void main(String[] args) throws InterruptedException {
        scheduleOnce();
        scheduleFixedRate();
        scheduleWithFixedDelay();
    }

    private static void scheduleOnce() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            Thread.currentThread().setName("scheduleOnce");
            IO.println(Thread.currentThread().getName() + " - Executing after 3 seconds!");
        };

        scheduledExecutorService.schedule(task, 3, TimeUnit.SECONDS);

        Thread.sleep(4000);
        scheduledExecutorService.shutdown();
    }

    private static void scheduleFixedRate() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            Thread.currentThread().setName("scheduleFixedRate");
            IO.println(Thread.currentThread().getName() + " - Running every 2 seconds - " + System.currentTimeMillis());
        };

        // starts after 1 second, repeats every 2 seconds
        scheduledExecutorService.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
    }

    private static void scheduleWithFixedDelay() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            Thread.currentThread().setName("scheduleWithFixedDelay");
            IO.println(Thread.currentThread().getName() + " - Running with fixed delay - " + System.currentTimeMillis());
            try {
                Thread.sleep(1500); // simula execução longa
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // start after 1 second, wait 2 seconds *after finishing* execution
        scheduledExecutorService.scheduleWithFixedDelay(task, 1, 2, TimeUnit.SECONDS);
    }

}
