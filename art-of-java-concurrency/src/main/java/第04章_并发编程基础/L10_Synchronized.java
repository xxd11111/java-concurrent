package 第04章_并发编程基础;

/**
 * @author xxd
 * @description volatile和synchronize关键字
 *
 * 在L10_Synchronized.class同级目录执行javap–v L10_Synchronized.class，部分相关输出如下所示：
 *
 * public static void main(java.lang.String[]);
 *         descriptor:([Ljava/lang/String;)V
 *         flags:ACC_PUBLIC,ACC_STATIC
 *         Code:
 *         stack=2,locals=3,args_size=1
 *         0:ldc           #2                  // class 第04章_并发编程基础/L10_Synchronized
 *         2:dup
 *         3:astore_1
 *         4:monitorenter
 *         5:aload_1
 *         6:monitorexit
 *         7:goto 15
 *         10:astore_2
 *         11:aload_1
 *         12:monitorexit
 *         13:aload_2
 *         14:athrow
 *         15:invokestatic  #3                  // Method m:()V
 *         18:return
 *         Exception table:
 *         from to target type
 *         5 7 10any
 *         10 13 10any
 *         LineNumberTable:
 *         line 15:0
 *         line 16:5
 *         line 18:15
 *         line 19:18
 *         LocalVariableTable:
 *         Start Length Slot Name Signature
 *         0 19 0args[Ljava/lang/String;
 *         StackMapTable:number_of_entries=2
 *         frame_type=255 // full_frame
 *         offset_delta=10
 *         locals=[class "[Ljava/lang/String;",
 *
 * public static synchronized void m();
 *         descriptor:()V
 *         flags:ACC_PUBLIC,ACC_STATIC,ACC_SYNCHRONIZED
 *         Code:
 *         stack=0,locals=0,args_size=0
 *         0:return
 *         LineNumberTable:
 *         line 22:0
 *         }
 *
 *
 * 上面class信息中，对于同步块的实现使用了monitorenter和monitorexit指令，而同步方法则 是依靠方法修饰符上的ACC_SYNCHRONIZED来完成的。
 * 无论采用哪种方式，其本质是对一 个对象的监视器（monitor）进行获取，而这个获取过程是排他的，也就是同一时刻只能有一个 线程获取到由synchronized所保护对象的监视器。
 *
 * 任意一个对象都拥有自己的监视器，当这个对象由同步块或者这个对象的同步方法调用 时，执行方法的线程必须先获取到该对象的监视器才能进入同步块或者同步方法，
 * 而没有获 取到监视器（执行该方法）的线程将会被阻塞在同步块和同步方法的入口处，进入BLOCKED 状态.
 * @date 2022-05-06
 */


public class L10_Synchronized {
    public static void main(String[] args) {
        // 对Synchronized Class对象进行加锁
        synchronized (L10_Synchronized.class) {
        }
        // 静态同步方法，对Synchronized Class对象进行加锁
        m();
    }

    public static synchronized void m() {
    }
}
