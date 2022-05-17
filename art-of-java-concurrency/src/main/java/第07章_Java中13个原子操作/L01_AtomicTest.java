package 第07章_Java中13个原子操作;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xxd
 * @description getAndIncrement
 * @date 2022-05-17
 */
public class L01_AtomicTest {
    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
    }

    /**
     * 如果当前数值是expected，则原子的将Java变量更新成x * @return 如果更新成功则返回true
     */
    public final native boolean compareAndSwapObject(Object o, long offset, Object expected, Object x);

    public final native boolean compareAndSwapInt(Object o, long offset, int expected, int x);

    public final native boolean compareAndSwapLong(Object o, long offset, long expected, long x);




}
