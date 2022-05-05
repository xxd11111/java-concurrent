package 第07章_取消与关闭;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Description 通过改写interrupt方法将非标准的取消操作封装在Thread中
 * @Author xxd
 * @Date 2021/10/18
 * @Version 1.0
 */
public class 通过改写interrupt方法将非标准的取消操作封装在Thread中 {
    public class ReaderThread extends Thread {
        private static final int BUFSZ = 512;
        private final Socket socket;
        private final InputStream in;
        public ReaderThread(Socket socket) throws IOException {
            this.socket = socket;
            this.in = socket.getInputStream();
        }
        public void interrupt() {
            try {
                socket.close();
            }
            catch (IOException ignored) { }
            finally {
                super.interrupt();
            }
        }
        public void run() {
            try {
                byte[] buf = new byte[BUFSZ];
                while (true) {
                    int count = in.read(buf);
                    if (count < 0)
                        break;
                    else if (count > 0)
                        processBuffer(buf, count);
                }
            } catch (IOException e) { /* Allow thread to exit */ }
        }

        public void processBuffer(byte[] buf, int count) {
        }
    }
}
