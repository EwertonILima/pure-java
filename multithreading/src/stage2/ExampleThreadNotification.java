package stage2;

import java.io.IO;
import java.util.LinkedList;
import java.util.Queue;

public class ExampleThreadNotification {
    public static void main(String[] args) {
        ExampleThreadNotification queue = new ExampleThreadNotification();

        Thread producer = new Thread(
                () -> {
                    for (var i = 1; i <= 10; i++) {
                        try {
                            queue.producer(i);
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        Thread consumer = new Thread(
                () -> {
                    for (var i = 1; i <= 10; i++) {
                        try {
                            queue.consumer();
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        producer.start();
        consumer.start();
    }

    private final Queue<Integer> queue = new LinkedList<>();

    public synchronized void producer(int value) throws InterruptedException {
        int LIMIT = 5;
        while (queue.size() == LIMIT) {
            IO.println("Full queue. Producer waiting...");
            wait(); // wait for space in queue
        }

        queue.add(value);
        IO.println("Produced: " + value);
        notify(); // notify consumer
    }

    public synchronized void consumer() throws InterruptedException {
        while (queue.isEmpty()) {
            IO.println("Empty queue. Consumer waiting");
            wait(); // wait for item in queue
        }

        int value = queue.remove();
        IO.println("Consumed: " + value);
        notify(); // notify producer
    }
}
