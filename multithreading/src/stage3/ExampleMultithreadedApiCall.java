package stage3;

import java.io.IO;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExampleMultithreadedApiCall {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        sequential();
        concurrent();
    }

    // Simulate external delay request
    private static String callService(String name, int delayInMillis) {
        try {
            IO.println("Starting " + name + " thread: " + Thread.currentThread().getName());
            Thread.sleep(delayInMillis);
            return name + " finished!";
        } catch (InterruptedException e) {
            return name + " failed!";
        }
    }

    // Sequential execution (1 at a time)
    public static void sequential() {
        IO.println("\n=== Sequential Execution ===");
        long start = System.currentTimeMillis();

        String s1 = callService("Service 1", 1000);
        String s2 = callService("Service 2", 1000);
        String s3 = callService("Service 3", 1000);

        long end = System.currentTimeMillis();
        IO.println(s1);
        IO.println(s2);
        IO.println(s3);
        IO.println("Total time: " + (end - start) + " ms");
    }

    // Concurrency execution using threads
    public static void concurrent() throws ExecutionException, InterruptedException {
        IO.println("\n=== Concurrent Execution");
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        long start = System.currentTimeMillis();

        Callable<String> task1 = () -> callService("Service 1", 1000);
        Callable<String> task2 = () -> callService("Service 2", 1000);
        Callable<String> task3 = () -> callService("service 3", 1000);

        Future<String> f1 = executorService.submit(task1);
        Future<String> f2 = executorService.submit(task2);
        Future<String> f3 = executorService.submit(task3);

        IO.println(f1.get());
        IO.println(f2.get());
        IO.println(f3.get());

        long end = System.currentTimeMillis();
        executorService.shutdown();
        IO.println("Total time: " + (end - start) + " ms");
    }
}
