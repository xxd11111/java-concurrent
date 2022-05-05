package 第10章_避免活跃性危险;

import java.util.Random;
import java.util.concurrent.locks.Lock;

/**
 * @Description L1004在典型条件下会发生死锁的循环
 * @Author xxd
 * @Date 2021/10/26
 * @Version 1.0
 */
public class L1004在典型条件下会发生死锁的循环 {

    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 1000000;

    public static void main(String[] args) {
        final Random rnd = new Random();
        final L1002动态的锁顺序死锁.Account[] accounts = new L1002动态的锁顺序死锁.Account[NUM_ACCOUNTS];

        for (int i = 0; i < accounts.length; i++)
            accounts[i] = new L1002动态的锁顺序死锁.Account();

        class TransferThread extends Thread {
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int fromAcct = rnd.nextInt(NUM_ACCOUNTS);
                    int toAcct = rnd.nextInt(NUM_ACCOUNTS);
                    L1002动态的锁顺序死锁.DollarAmount amount = new L1002动态的锁顺序死锁.DollarAmount(rnd.nextInt(1000));
                    try {
                        L1002动态的锁顺序死锁.transferMoney(accounts[fromAcct], accounts[toAcct], amount);
                    } catch (L1002动态的锁顺序死锁.InsufficientFundsException ignored) {
                    }
                }
            }
        }
        for (int i = 0; i < NUM_THREADS; i++)
            new TransferThread().start();
    }

}

