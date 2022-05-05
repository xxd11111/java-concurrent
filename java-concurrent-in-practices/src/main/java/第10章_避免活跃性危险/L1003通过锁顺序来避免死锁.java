package 第10章_避免活跃性危险;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @Description L1003通过锁顺序来避免死锁
 * @Author xxd
 * @Date 2021/10/26
 * @Version 1.0
 */
public class L1003通过锁顺序来避免死锁 {
    private static Random rnd = new Random();

    public boolean transferMoney(Account fromAcct,
                                 Account toAcct,
                                 DollarAmount amount,
                                 long timeout,
                                 TimeUnit unit)
            throws InsufficientFundsException, InterruptedException {
        long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
        long randMod = getRandomDelayModulusNanos(timeout, unit);
        long stopTime = System.nanoTime() + unit.toNanos(timeout);

        while (true) {
            if (fromAcct.lock.tryLock()) {
                try {
                    if (toAcct.lock.tryLock()) {
                        try {
                            if (fromAcct.getBalance().compareTo(amount) < 0)
                                throw new InsufficientFundsException();
                            else {
                                fromAcct.debit(amount);
                                toAcct.credit(amount);
                                return true;
                            }
                        } finally {
                            toAcct.lock.unlock();
                        }
                    }
                } finally {
                    fromAcct.lock.unlock();
                }
            }
            if (System.nanoTime() < stopTime)
                return false;
            NANOSECONDS.sleep(fixedDelay + rnd.nextLong() % randMod);
        }
    }

    private static final int DELAY_FIXED = 1;
    private static final int DELAY_RANDOM = 2;

    static long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
        return DELAY_FIXED;
    }

    static long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
        return DELAY_RANDOM;
    }

    static class DollarAmount implements Comparable<DollarAmount> {
        public int compareTo(DollarAmount other) {
            return 0;
        }

        DollarAmount(int dollars) {
        }
    }

    class Account {
        public Lock lock;

        void debit(DollarAmount d) {
        }

        void credit(DollarAmount d) {
        }

        DollarAmount getBalance() {
            return null;
        }
    }

    class InsufficientFundsException extends Exception {
    }
}
