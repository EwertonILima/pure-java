package stage1;

import java.io.IO;
import java.util.concurrent.atomic.AtomicInteger;

public class ExampleRaceCondition {
    public static void main(String[] args) {
        ExampleRaceCondition currentAccount = new ExampleRaceCondition();

        Runnable withdraw1 = () -> currentAccount.withdraw("Ewerton", 80);
        Runnable withdraw2 = () -> currentAccount.withdraw("Maria", 80);

//        new Thread(withdraw1).start();
//        new Thread(withdraw2).start();

        Runnable withdrawSynchronized1 = () -> currentAccount.withdrawSynchronized("Ewerton", 80);
        Runnable withdrawSynchronized2 = () -> currentAccount.withdrawSynchronized("Maria", 80);

        new Thread(withdrawSynchronized1).start();
        new Thread(withdrawSynchronized2).start();

        Runnable withdrawAtomicBalance1 = () -> currentAccount.withdrawAtomicBalance("Ewerton", 80);
        Runnable withdrawAtomicBalance2 = () -> currentAccount.withdrawAtomicBalance("Maria", 80);

//        new Thread(withdrawAtomicBalance1).start();
//        new Thread(withdrawAtomicBalance2).start();

    }

    private int balance = 100;
    private final AtomicInteger atomicBalance = new AtomicInteger(100);

    //Synchronized blocks access to the method for other threads while a thread is executing.
    //"Only one thread can enter here at a time. If another tries, it waits outside."
    //• It ensures that only one thread at a time can modify the balance.
    public synchronized void withdrawSynchronized(String name, int value) {

        if (balance >= value) {
            IO.println("Sync " + name + " will withdraw U$" + value);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            balance -= value;
            IO.println("Sync " + name + " withdrew. Remaining balance: U$" + balance);

        } else {
            IO.println("Sync " + name + " tried to withdraw U$" + value + " but balance was U$" + balance);
        }
    }

    public void withdrawAtomicBalance(String name, int value) {
        int currentBalance;
        do {
            currentBalance = atomicBalance.get();
            if (currentBalance < value) {
                IO.println(name + " tried to withdraw U$" + value + " but balance was U$" + currentBalance);
                return;
            }

            IO.println(name + " will withdraw U$" + value);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while (!atomicBalance.compareAndSet(currentBalance, currentBalance - value));

        IO.println(name + " withdrew. Remaining balance: U$" + balance);
    }

    public void withdraw(String name, int value) {
        if (balance >= value) {
            IO.println(name + " will withdraw U$" + value);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            balance -= value;
            IO.println(name + " withdrew. Remaining balance: U$" + balance);

        } else {
            IO.println(name + " tried to withdraw U$" + value + " but balance was U$" + balance);
        }
    }
}
