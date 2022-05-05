package 第16章_java内存模型;

/**
 * @Description TODO
 * @Author xxd
 * @Date 2021/11/4
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        testSafeStates();
    }

    static void testSafeStates(){
        SafeStates safeStates1 = new SafeStates();
        SafeStates safeStates2 = new SafeStates();
        System.out.println(safeStates1.getStates()== safeStates2.getStates());
    }
}
