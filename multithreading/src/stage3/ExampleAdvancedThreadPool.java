package stage3;

import java.io.IO;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExampleAdvancedThreadPool {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(2);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                4,
                3, TimeUnit.SECONDS,
                blockingQueue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> {
                    System.out.printf("🧵 Ativas: %d | 💤 Na fila: %d | Máximo: %d | Completas: %d\n",
                            threadPoolExecutor.getActiveCount(),
                            threadPoolExecutor.getQueue().size(),
                            threadPoolExecutor.getPoolSize(),
                            threadPoolExecutor.getCompletedTaskCount());
                }, 0, 1, TimeUnit.SECONDS);

        for (var i = 1; i <= 10; i++) {
            int taskId = i;
            threadPoolExecutor.execute(
                    () -> {
                        String threadName = Thread.currentThread().getName();
                        System.out.printf("✅ Starting task %d on thread %s\n", taskId, threadName);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.printf("🏁 Finishing task %d on thread %s\n", taskId, threadName);
                    });
        }
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);
        IO.println("🚪 Finished.");
    }
}
