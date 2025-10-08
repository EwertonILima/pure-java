package stage1;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ExampleThread extends Thread {
    public static void main(String[] args) {
        ExampleThread t1 = new ExampleThread();
        t1.start();
        System.out.println("Executing on: " + Thread.currentThread().getName());
    }

    public void run() {
        System.out.println("Executing on thread: " + Thread.currentThread().getName());
    }
}