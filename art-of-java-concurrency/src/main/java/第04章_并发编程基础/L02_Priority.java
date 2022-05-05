package 第04章_并发编程基础;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xxd
 * @description
 * 默认优先级5， 优先级越高，分配时间片数量要多余优先级低的线程
 *
 * {从输出结果可以看出，
 * 优先级1与优先级10的job计数结果非常接近，没有明显差距。
 * 这表示程序正确性不能依赖线程的优先级高低。
 * 注意：线程优先级不能作为程序正确性的依赖，因为操作系统可以完全不用理会Java 线程对于优先级的设定。
 * 笔者的环境为：Mac OS X 10.10，Java版本为1.7.0_71，经过笔者验证 该环境下所有Java线程优先级均为5（通过jstack查看），
 * 对线程优先级的设置会被忽略。另外， 尝试在Ubuntu 14.04环境下运行该示例，输出结果也表示该环境忽略了线程优先级的设置。}
 * 以上该为书上结论
 *
 * 本人测试实际结果：
 * Job Priority : 1, Count : 393952
 * Job Priority : 1, Count : 394049
 * Job Priority : 1, Count : 394048
 * Job Priority : 1, Count : 394138
 * Job Priority : 1, Count : 394132
 * Job Priority : 10, Count : 3093159
 * Job Priority : 10, Count : 3098626
 * Job Priority : 10, Count : 3084829
 * Job Priority : 10, Count : 3081967
 * Job Priority : 10, Count : 3099984
 * 本人结果还是跟优先级有关的 win10 jdk1.8_202
 *
 * @date 2022-05-05
 */
public class L02_Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    public static void main(String[] args) throws Exception {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        for (Job job : jobs) {
            System.out.println("Job Priority : " + job.priority + ", Count : " + job.jobCount);
        }
    }

    static class Job implements Runnable {
        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}
