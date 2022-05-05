package 第08章_线程池的使用;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.CountDownLatch;

/**
 * @Description ValueLatch
 * @Author xxd
 * @Date 2021/10/26
 * @Version 1.0
 */

public class ValueLatch<T> {
    @GuardedBy("this")
    private T value = null;
    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return (done.getCount() == 0);
    }

    public synchronized void setValue(T newValue) {
        if (!isSet()) {
            value = newValue;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException {
        done.await();
        synchronized (this) {
            return value;
        }
    }
}
