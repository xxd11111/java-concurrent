package 第04章_并发编程基础;

import java.util.concurrent.TimeUnit;

/**
 * @author xxd
 * @description ThreadLocal的使用
 * ThreadLocal，即线程变量，是一个以ThreadLocal对象为键、任意对象为值的存储结构。这 个结构被附带在线程上，也就是说一个线程可以根据一个ThreadLocal对象查询到绑定在这个线程上的一个值.
 * <p>
 * 在代码所示的例子中，构建了一个常用的Profiler类，它具有begin()和end()两个 方法，而end()方法返回从begin()方法调用开始到end()方法被调用时的时间差，单位是毫秒。
 * 输出结果:
 * Cost: 1012 mills
 * <p>
 * Profiler可以被复用在方法调用耗时统计的功能上，在方法的入口前执行begin()方法，在方法调用后执行end()方法，好处是两个方法的调用不用在一个方法或者类中，
 * 比如在AOP（面 向方面编程）中，可以在方法调用前的切入点执行begin()方法，而在方法调用后的切入点执行 end()方法，这样依旧可以获得方法的执行耗时。
 * @date 2022-05-06
 */
public class L15_Profiler {
    // 第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws Exception {
        L15_Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + L15_Profiler.end() + " mills");
    }
}
