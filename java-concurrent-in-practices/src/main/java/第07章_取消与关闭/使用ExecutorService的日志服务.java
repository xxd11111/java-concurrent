package 第07章_取消与关闭;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * @Description 使用ExecutorService的日志服务，使用shutdown正常关闭，使用shutNow强制关闭
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 使用ExecutorService的日志服务 {
    public class LogService {
        private final ExecutorService exec = newSingleThreadExecutor();
        private final PrintWriter writer;
        int TIMEOUT = 10;

        public LogService(PrintWriter writer) {
            this.writer = writer;
        }

        public void start() { }
        public void stop() throws InterruptedException {
            try {
                exec.shutdown();
                exec.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
            } finally {
                writer.close();
            }
        }
        public void log(String msg) {
            try {
                exec.execute(new WriteTask(msg));
            } catch (RejectedExecutionException ignored) { }
        }

        public class WriteTask implements Runnable {
            public WriteTask(String msg) {
            }

            @Override
            public void run() {

            }
        }
    }
}
