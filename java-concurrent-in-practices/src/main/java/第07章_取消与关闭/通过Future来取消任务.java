package 第07章_取消与关闭;

import java.util.concurrent.*;

/**
 * @Description 通过Future来取消任务
 * @Author xxd
 * @Date 2021/10/18
 * @Version 1.0
 */
public class 通过Future来取消任务 {
    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        ScheduledThreadPoolExecutor taskExec = new ScheduledThreadPoolExecutor(10);
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // task will be cancelled below
        } catch (ExecutionException e) {
            // exception thrown in task; rethrow
            throw launderThrowable(e.getCause());
        } finally {
            // Harmless if task already completed
            task.cancel(true); // interrupt if running
        }
    }

    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException("Not unchecked", t);
    }
}
