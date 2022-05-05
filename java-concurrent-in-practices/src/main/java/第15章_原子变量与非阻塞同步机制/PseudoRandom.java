package 第15章_原子变量与非阻塞同步机制;

/**
 * PseudoRandom
 *
 * @author Brian Goetz and Tim Peierls
 */
public class PseudoRandom {
    int calculateNext(int prev) {
        prev ^= prev << 6;
        prev ^= prev >>> 21;
        prev ^= (prev << 7);
        return prev;
    }

    public static void main(String[] args) {
        System.out.println(new PseudoRandom().calculateNext(120));
    }
}