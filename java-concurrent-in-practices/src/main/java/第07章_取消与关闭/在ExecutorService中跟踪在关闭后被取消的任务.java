package 第07章_取消与关闭;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description 在ExecutorService中跟踪在关闭后被取消的任务
 * 在关闭过程中判断正在执行的任务，记录哪些任务是在关闭后取消的
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 在ExecutorService中跟踪在关闭后被取消的任务 {
    public class TrackingExecutor extends AbstractExecutorService {
        private final ExecutorService exec;
        private final Set<Runnable> tasksCancelledAtShutdown =
                Collections.synchronizedSet(new HashSet<Runnable>());

        public TrackingExecutor(ExecutorService exec) {
            this.exec = exec;
        }

        public void shutdown() {
            exec.shutdown();
        }

        public List<Runnable> shutdownNow() {
            return exec.shutdownNow();
        }

        public boolean isShutdown() {
            return exec.isShutdown();
        }

        public boolean isTerminated() {
            return exec.isTerminated();
        }

        public boolean awaitTermination(long timeout, TimeUnit unit)
                throws InterruptedException {
            return exec.awaitTermination(timeout, unit);
        }

        public List<Runnable> getCancelledTasks() {
            if (!exec.isTerminated())
                throw new IllegalStateException(/*...*/);
            return new ArrayList<Runnable>(tasksCancelledAtShutdown);
        }

        public void execute(final Runnable runnable) {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        if (isShutdown()
                                && Thread.currentThread().isInterrupted())
                            tasksCancelledAtShutdown.add(runnable);
                    }
                }
            });
        }
    }
}
