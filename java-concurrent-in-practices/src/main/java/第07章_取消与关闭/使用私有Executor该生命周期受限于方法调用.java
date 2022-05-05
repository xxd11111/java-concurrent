package 第07章_取消与关闭;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description 使用私有Executor该生命周期受限于方法调用,
 *  checkMail在多台主机上并行检查新邮件。
 *  他创建一个私有Executor，并向每台主机提交一个任务。
 *  当所有邮件检查任务执行完成后，关闭Executor并等待结束。
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 使用私有Executor该生命周期受限于方法调用 {
    public class CheckForMail {
        public boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit)
                throws InterruptedException {
            ExecutorService exec = Executors.newCachedThreadPool();
            final AtomicBoolean hasNewMail = new AtomicBoolean(false);
            try {
                for (final String host : hosts)
                    exec.execute(new Runnable() {
                        public void run() {
                            if (checkMail(host))
                                hasNewMail.set(true);
                        }
                    });
            } finally {
                exec.shutdown();
                exec.awaitTermination(timeout, unit);
            }
            return hasNewMail.get();
        }

        private boolean checkMail(String host) {
            // Check for mail
            return false;
        }
    }
}
