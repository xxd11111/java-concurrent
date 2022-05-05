package 第08章_线程池的使用;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @Description 在单线程Executor中任务发生死锁 不要这么做
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 在单线程Executor中任务发生死锁 {
    public static class ThreadDeadlock {
        static ExecutorService executorService = Executors.newSingleThreadExecutor();

        public static class RenderPageTask implements Callable<String> {
            @Override
            public String call() throws Exception {
                Future<String> header, footer;
                header = executorService.submit(new LoadFileTask("header.html"));
                footer = executorService.submit(new LoadFileTask("footer.html"));
                String page = renderBody();
                return header.get() + page + footer.get();
            }

            private String renderBody() {
                return "page";
            }
        }

        public static class LoadFileTask implements Callable<String> {
            public LoadFileTask(String s) {
            }

            @Override
            public String call() throws Exception {
                wait();
                return "null";
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> submit = executorService.submit(new ThreadDeadlock.RenderPageTask());
        String s = submit.get();
        System.out.println(s);
    }
}
