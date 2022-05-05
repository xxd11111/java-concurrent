package 第08章_线程池的使用;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * @Description 增加了日志和计时等功能的线程池
 * @Author xxd
 * @Date 2021/10/21
 * @Version 1.0
 */
public class 增加了日志和计时等功能的线程池 {
    public class TimingThreadPool extends ThreadPoolExecutor {

        public TimingThreadPool() {
            super(1, 1, 0L, TimeUnit.SECONDS, null);
        }

        //采用threadLocal存时间
        private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
        private final Logger log = Logger.getLogger("TimingThreadPool");
        private final AtomicLong numTasks = new AtomicLong();
        private final AtomicLong totalTime = new AtomicLong();

        //日志功能，执行前
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            log.fine(String.format("Thread %s: start %s", t, r));
            startTime.set(System.nanoTime());
        }

        //日志功能，执行后
        protected void afterExecute(Runnable r, Throwable t) {
            try {
                long endTime = System.nanoTime();
                long taskTime = endTime - startTime.get();
                numTasks.incrementAndGet();
                totalTime.addAndGet(taskTime);
                log.fine(String.format("Thread %s: end %s, time=%dns",
                        t, r, taskTime));
            } finally {
                super.afterExecute(r, t);
            }
        }

        //日志功能，终止
        protected void terminated() {
            try {
                log.info(String.format("Terminated: avg time=%dns",
                        totalTime.get() / numTasks.get()));
            } finally {
                super.terminated();
            }
        }
    }
}
