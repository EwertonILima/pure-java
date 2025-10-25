package stage3;

import java.io.IO;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExampleThreadPoolExecutor {
    public static void main(String[] args) {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(3);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                2,
                0L, TimeUnit.SECONDS,
                blockingQueue,
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
                        IO.println("❌ Task rejected: " + task.toString());
                    }
                }
        );

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            threadPoolExecutor.execute(
                    () -> {
                        IO.println("✅ Executing task " + taskId + " in thread " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
        }
        threadPoolExecutor.shutdown();
    }
}
