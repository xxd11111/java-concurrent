package 第04章_并发编程基础;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xxd
 * @description 线程池技术的简单Web服务器
 * 如果Web服务器是单线程的，多线程的浏览器也没有用武之地，因为服务端还是一个请求 一个请求的顺序处理。
 * 因此，大部分Web服务器都是支持并发访问的。常用的Java Web服务器， 如Tomcat、Jetty，在其处理请求的过程中都使用到了线程池技术。
 * <p>
 * SimpleHttpServer在建立了与客户端的连接之后，并不会处理客户端的请求， 而是将其包装成HttpRequestHandler并交由线程池处理。
 * 在线程池中的Worker处理客户端请求 的同时，SimpleHttpServer能够继续完成后续客户端连接的建立，不会阻塞后续客户端的请求。
 *
 * 项目运行后，浏览器输入 http://localhost:8080/L22_Index.html 可获取相关资源
 *
 * @date 2022-05-08
 */
public class L21_SimpleHttpServer {
    // 处理HttpRequest的线程池
    static L19_ThreadPool<HttpRequestHandler> threadPool = new L20_DefaultThreadPool<>(1);
    // SimpleHttpServer的根路径
    static String basePath;
    static ServerSocket serverSocket;
    // 服务监听端口
    static int port = 8080;

    public static void setPort(int port) {
        if (port > 0) {
            L21_SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            L21_SimpleHttpServer.basePath = basePath;
        }
    }



    // 启动SimpleHttpServer
    public static void start() throws Exception {
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            // 接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }

    static class HttpRequestHandler implements Runnable {
        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line = null;
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                // 由相对路径计算出绝对路径
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                // 如果请求资源的后缀为jpg或者ico，则读取资源并输出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");
                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: text/html; charset=UTF-8");
                    out.println("");
                    while ((line = br.readLine()) != null) {
                        out.println(line);
                    }
                }
                out.flush();
            } catch (Exception ex) {
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {
                close(br, in, reader, out, socket);
            }
        }

        // 关闭流或者Socket
        private static void close(Closeable... closeables) {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    try {
                        closeable.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }

        public static void main(String[] args) throws Exception {
            setBasePath("E:\\share-data\\git\\java-concurrent\\art-of-java-concurrency\\src\\main\\java\\第04章_并发编程基础");
            start();
        }
    }
}