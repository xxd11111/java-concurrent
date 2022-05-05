package 第04章_并发编程基础;

import java.security.AccessControlContext;

import static java.lang.Thread.currentThread;

/**
 * @author xxd
 * @description 构造线程
 * 以下为Thread源码中部分初始化片段
 * 一个新构造的线程对象是由其parent线程来进行空间分配的，而child线程 继承了parent是否为Daemon、优先级和加载资源的contextClassLoader以及可继承的 ThreadLocal，
 * 同时还会分配一个唯一的ID来标识这个child线程。至此，一个能够运行的线程对 象就初始化好了，在堆内存中等待着运行
 * @date 2022-05-05
 */
public class L06_Thread {
    private void init(ThreadGroup g, Runnable target, String name, long stackSize, AccessControlContext acc) {
        //if (name == null) {
        //    throw new NullPointerException("name cannot be null");
        //}
        //// 当前线程就是该线程的父线程
        //Thread parent = currentThread();
        //this.group = g;
        //// 将daemon、priority属性设置为父线程的对应属性
        //this.daemon = parent.isDaemon();
        //this.priority = parent.getPriority();
        //this.name = name.toCharArray();
        //this.target = target;
        //setPriority(priority);
        //// 将父线程的InheritableThreadLocal复制过来
        //if (parent.inheritableThreadLocals != null)
        //    this.inheritableThreadLocals = ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
        //// 分配一个线程ID
        //tid = nextThreadID();
    }
}
