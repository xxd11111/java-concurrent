package 第07章_取消与关闭;

import net.jcip.annotations.GuardedBy;

import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @Description 使用TrackingExecutorService来保存未完成的任务以备后续执行
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 使用TrackingExecutorService来保存未完成的任务以备后续执行 {
    public abstract class WebCrawler {
        private volatile TrackingExecutor exec;
        @GuardedBy("this")
        private final Set<URL> urlsToCrawl = new HashSet<URL>();

        private final ConcurrentMap<URL, Boolean> seen = new ConcurrentHashMap<URL, Boolean>();
        private static final long TIMEOUT = 500;
        private final TimeUnit UNIT = MILLISECONDS;

        public WebCrawler(URL startUrl) {
            urlsToCrawl.add(startUrl);
        }

        public synchronized void start() {
            exec = new TrackingExecutor(Executors.newCachedThreadPool());
            for (URL url : urlsToCrawl) submitCrawlTask(url);
            urlsToCrawl.clear();
        }

        public synchronized void stop() throws InterruptedException {
            try {
                saveUncrawled(exec.shutdownNow());
                if (exec.awaitTermination(TIMEOUT, UNIT))
                    saveUncrawled(exec.getCancelledTasks());
            } finally {
                exec = null;
            }
        }

        protected abstract List<URL> processPage(URL url);

        private void saveUncrawled(List<Runnable> uncrawled) {
            for (Runnable task : uncrawled)
                urlsToCrawl.add(((CrawlTask) task).getPage());
        }

        private void submitCrawlTask(URL u) {
            exec.execute(new CrawlTask(u));
        }

        private class CrawlTask implements Runnable {
            private final URL url;

            CrawlTask(URL url) {
                this.url = url;
            }

            private int count = 1;

            boolean alreadyCrawled() {
                return seen.putIfAbsent(url, true) != null;
            }

            void markUncrawled() {
                seen.remove(url);
                System.out.printf("marking %s uncrawled%n", url);
            }

            public void run() {
                for (URL link : processPage(url)) {
                    if (Thread.currentThread().isInterrupted())
                        return;
                    submitCrawlTask(link);
                }
            }

            public URL getPage() {
                return url;
            }
        }
    }


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
