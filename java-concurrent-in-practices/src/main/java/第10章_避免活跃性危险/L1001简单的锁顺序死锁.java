package 第10章_避免活跃性危险;

/**
 * @Description L1001简单的锁顺序死锁，不要这么做
 * @Author xxd
 * @Date 2021/10/26
 * @Version 1.0
 */
public class L1001简单的锁顺序死锁 {
    public class LeftRightDeadlock {
        private final Object left = new Object();
        private final Object right = new Object();

        public void leftRight() {
            synchronized (left) {
                synchronized (right) {
                    doSomething();
                }
            }
        }

        public void rightLeft() {
            synchronized (right) {
                synchronized (left) {
                    doSomethingElse();
                }
            }
        }

        void doSomething() {
        }

        void doSomethingElse() {
        }
    }
}
