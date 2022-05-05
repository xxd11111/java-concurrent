package 第14章_构建自定义的同步工具;

import net.jcip.annotations.*;

/**
 * ThreadGate  可用用于执行等待打开，及等待关闭
 *      当线程通过时，未打开则会进入wait中，打开后，计数++，线程执行通过，即使打开后突然关闭也会改变!isOpen && arrivalGeneration == generation条件，不会出现一直卡住情况
 */
@ThreadSafe
public class ThreadGate {
    // CONDITION-PREDICATE: opened-since(n) (isOpen || generation>n)
    @GuardedBy("this") private boolean isOpen;
    @GuardedBy("this") private int generation;

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    // BLOCKS-UNTIL: opened-since(generation on entry)
    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation)
            wait();
    }
}
