package 第08章_线程池的使用;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

/**
 * @Description 使用Semaphore来控制任务的提交速率
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 使用Semaphore来控制任务的提交速率 {
    @ThreadSafe
    public class BoundedExecutor {
        private final Executor exec;
        private final Semaphore semaphore;

        public BoundedExecutor(Executor exec, int bound) {
            this.exec = exec;
            this.semaphore = new Semaphore(bound);
        }

        public void submitTask(final Runnable command)
                throws InterruptedException {
            semaphore.acquire();
            try {
                exec.execute(new Runnable() {
                    public void run() {
                        try {
                            command.run();
                        } finally {
                            semaphore.release();
                        }
                    }
                });
            } catch (RejectedExecutionException e) {
                semaphore.release();
            }
        }
    }
}
