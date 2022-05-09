package 第04章_并发编程基础;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author xxd
 * @description 管道输入/输出流
 *
 * 管道输入/输出流和普通的文件输入/输出流或者网络输入/输出流不同之处在于，它主要 用于线程之间的数据传输，而传输的媒介为内存。
 * 管道输入/输出流主要包括了如下4种具体实现：PipedOutputStream、PipedInputStream、 PipedReader和PipedWriter，前两种面向字节，而后两种面向字符。
 *
 * 所示的代码中，创建了printThread，它用来接受main线程的输入，任何 main线程的输入均通过PipedWriter写入，而printThread在另一端通过PipedReader将内容读出 并打印
 *
 * 输出结果：
 * Repeat my words.
 * Repeat my words.
 *
 * 对于Piped类型的流，必须先要进行绑定，也就是调用connect()方法，如果没有将输入/输 出流绑定起来，对于该流的访问将会抛出异常。
 *
 * @date 2022-05-06
 */
public class L12_Piped {
    public static void main(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        // 将输出流和输入流进行连接，否则在使用时会抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        } finally {
            out.close();
        }
    }

    static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException ex) {
            }
        }
    }
}
