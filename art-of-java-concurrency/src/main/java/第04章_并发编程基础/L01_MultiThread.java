package 第04章_并发编程基础;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 *
 * @description 查看java程序包含哪些线程
 *
 * 结果：
 * 8--JDWP Command Reader
 * 7--JDWP Event Helper Thread
 * 6--JDWP Transport Listener: dt_socket
 * 5--Attach Listener
 * 4--Signal Dispatcher 分发处理发送给JVM信号的线程
 * 3--Finalizer 调用对象finalize方法的线程
 * 2--Reference Handler 清除reference的线程
 * 1--main 用户程序入口
 * @author xxd
 * @date 2022-05-05
 */
public class L01_MultiThread {
    public static void main(String[] args) {
        //获取java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        //遍历线程信息，仅打印线程id和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId() +"--"+ threadInfo.getThreadName());
        }
    }
}
