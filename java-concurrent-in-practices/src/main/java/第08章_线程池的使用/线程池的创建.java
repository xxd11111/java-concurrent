package 第08章_线程池的使用;

import java.util.concurrent.*;

/**
 * @Description 线程池的创建
 * @Author xxd
 * @Date 2021/10/19
 * @Version 1.0
 */
public class 线程池的创建 {
    public static void main(String[] args) {
        //基本为12，最大30,存活时间200秒，用长度为12的ArrayBlockingQueue存放超出的任务
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(12,30,200, TimeUnit.SECONDS, new ArrayBlockingQueue<>(12));
        //设置饱和策略
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //最大和基本大小为12，无存活时间限制，使用无界的linkedBlockingQueue存放超出的任务
        ExecutorService executorService = Executors.newFixedThreadPool(12);
        //基本为0，最大integer.max_value,存活时间1分钟，使用SynchronousQueue 同步移交任务
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        //单线程，使用无界的linkedBlockingQueue存放超出的任务
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();

        // executorService.submit();
        // threadPoolExecutor.submit();

        //linkedBlockingQueue,ArrayBlockingQueue都是FIFO的，PriorityBlockingQueue优先级队列，可以使用自然顺序或者comparator来定义（任务实现了comparable）
        //为线程设置合理大小，任务间存在依赖关系有界线程可能导致“饥饿”死锁问题

        //newCachedThreadPool比固定大小的有更好的排队性能，大小不限制，但是可能会发生过载问题
        //优势原因使用了SynchronousQueue而不是LinkedBlockedQueue

        //仅当工作队列已满时ThreadPoolExecutor才会创建新的线程，
        // 可以通过allowCoreThreadTimeOut来使线程中的所有线程超时（在没有任务的情况，可以设置为0，销毁所有线程）

        //饱和策略，当有界队列填满后，ThreadPoolExecutor可以通过setRejectedExecutionHandler来修改。默认是“中止（Abort）”，超出会抛异常。
        //“抛弃（Discard）”会悄悄抛弃该任务。
        //“抛弃最旧的（DiscardOldest）”会抛弃下一个将被执行的任务，然后尝试重新提交新的任务
        // “调用者运行（Caller-Runs）”不抛弃也不抛出异常，将任务回退到调用者
    }
}
