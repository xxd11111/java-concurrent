package 第08章_线程池的使用;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description PuzzleNode  用于记录节点，当找到解决方法时候，逐级回退记录返回node集合
 * @Author xxd
 * @Date 2021/10/22
 * @Version 1.0
 */
public class PuzzleNode <P, M> {
    final P pos;
    final M move;
    final PuzzleNode<P, M> prev;

    public PuzzleNode(P pos, M move, PuzzleNode<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList() {
        List<M> solution = new LinkedList<M>();
        for (PuzzleNode<P, M> n = this; n.move != null; n = n.prev)
            solution.add(0, n.move);
        return solution;
    }
}