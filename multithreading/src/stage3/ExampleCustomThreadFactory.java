package stage3;

import java.io.IO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExampleCustomThreadFactory {
    public static void main(String[] args) {
        ThreadFactory namedFactory = new ThreadFactory() {
            private int count = 1;

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("Worker-" +  count++);
                t.setPriority(Thread.NORM_PRIORITY);
                return t;
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(3, namedFactory);

        for(var i = 0; i <= 5; i++) {
            executorService.submit(
                    () -> {
                        IO.println("🚀 Running on " + Thread.currentThread().getName());
                    }
            );
        }
        executorService.shutdown();
    }
}
