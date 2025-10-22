package stage3;

import java.io.IO;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExampleFutureTimeout {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> timeConsumingTask = () -> {
            IO.println("Starting heavy task...");
            Thread.sleep(8000); // takes 8 seconds
            return "Task successfully finished";
        };

        Future<String> future = executorService.submit(timeConsumingTask);

        try {
            // Wait up to 5 seconds
            String result = future.get(5, TimeUnit.SECONDS);
            IO.println("Result: " + result);
        } catch (TimeoutException e) {
            IO.println("⏰ Timeout! Canceling the task...");
            future.cancel(true); // stop the thread
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        IO.println("Application completed.");
    }
}
