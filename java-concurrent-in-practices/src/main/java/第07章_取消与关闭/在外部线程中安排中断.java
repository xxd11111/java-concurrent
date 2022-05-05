package 第07章_取消与关闭;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 不建议，无法知道该线程是完成后退出，还是未完成后退出
 * @Author xxd
 * @Date 2021/10/18
 * @Version 1.0
 */
public class 在外部线程中安排中断 {
    private static final ScheduledExecutorService cancelExec = new ScheduledThreadPoolExecutor(10);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(new Runnable() {
            public void run() { taskThread.interrupt(); }
        }, timeout, unit);
        r.run();
    }
}
