package stage3;

import java.io.IO;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExampleCompletableFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 3 tasks simulating APIs request
        CompletableFuture<String> api1 = CompletableFuture.supplyAsync(() -> {
            simulateDelay(2);
            return "API 1 responded";
        }, executorService);

        CompletableFuture<String> api2 = CompletableFuture.supplyAsync(() -> {
            simulateDelay(4);
            return "API 2 responded";
        }, executorService);

        CompletableFuture<String> api3 = CompletableFuture.supplyAsync(() -> {
            simulateDelay(3);
            if (new Random().nextBoolean()) {
                throw new RuntimeException("API 3 Error");
            }
            return "API 3 responded";
        });

        CompletableFuture<Object> fasterAPI = CompletableFuture.anyOf(api1, api2, api3);
        fasterAPI.thenAccept(result -> IO.println("⚡ Faster: " + result));

        CompletableFuture<Void> allAPIs = CompletableFuture.allOf(api1, api2, api3);
        allAPIs.thenRun(() -> {
            try {
                IO.println("✅ They all finished:");
                IO.println("🔸 " + api1.get());
                IO.println("🔸 " + api2.get());
                IO.println("🔸 " + api3.get());
            } catch (Exception e) {
                IO.println("❌ Error fetching results: " + e.getMessage());
            }
            executorService.shutdown();
        });

        try {
            allAPIs.get(6, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            IO.println("⏱️ Timeout: One of the tasks took too long.");
        } catch (Exception e) {
            IO.println("❌ General error: " + e.getMessage());
        }

        executorService.shutdown();
    }

    private static void simulateDelay(int seconds) {
        try {
            IO.println("⏳ Simulating delay of " + seconds + "s...");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            IO.println("❗ Interrupted");
        }
    }
}
