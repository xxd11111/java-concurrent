package 第08章_线程池的使用;

import java.util.Arrays;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description 自定义线程工厂  定制Thread基类
 * @Author xxd
 * @Date 2021/10/20
 * @Version 1.0
 */
public class 自定义线程工厂定制Thread基类 {
    public class MyThreadFactory implements ThreadFactory {
        private final String poolName;

        public MyThreadFactory(String poolName){
            this.poolName = poolName;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            return new MyAppThread(runnable, poolName);
        }
    }

    //定制Thread基类，为线程指定名字，设置自定义UncaughtExceptionHandler向Logger中写入信息，维护一些统计信息
    public static class MyAppThread extends Thread {
        public static final String DEFAULT_NAME = "MyAppThread";
        private static volatile boolean debugLifecycle = false;
        private static final AtomicInteger created = new AtomicInteger();
        private static final AtomicInteger alive = new AtomicInteger();
        private static final Logger log = Logger.getAnonymousLogger();

        public MyAppThread(Runnable r) {
            this(r, DEFAULT_NAME);
        }

        public MyAppThread(Runnable runnable, String name) {
            super(runnable, name + "-" + created.incrementAndGet());
            setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                public void uncaughtException(Thread t,
                                              Throwable e) {
                    log.log(Level.SEVERE,
                            "UNCAUGHT in thread " + t.getName(), e);
                }
            });
        }

        public void run() {
            // Copy debug flag to ensure consistent value throughout.
            boolean debug = debugLifecycle;
            if (debug) log.log(Level.FINE, "Created " + getName());
            try {
                alive.incrementAndGet();
                super.run();
            } finally {
                alive.decrementAndGet();
                if (debug) log.log(Level.FINE, "Exiting " + getName());
            }
        }

        public static int getThreadsCreated() {
            return created.get();
        }

        public static int getThreadsAlive() {
            return alive.get();
        }

        public static boolean getDebug() {
            return debugLifecycle;
        }

        public static void setDebug(boolean b) {
            debugLifecycle = b;
        }
    }

}
