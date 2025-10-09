package stage1;

public class ExampleRunnable {
    public static void main(String[] args) {
        Runnable task = () -> {
            System.out.println("Executing on: " + Thread.currentThread().getName());
        };

        Thread thread = new Thread(task);
        thread.start();
        System.out.println("Main thread: " + Thread.currentThread().getName());
    }
}
