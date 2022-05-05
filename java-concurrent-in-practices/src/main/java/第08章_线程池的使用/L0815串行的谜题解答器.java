package 第08章_线程池的使用;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description 串行的谜题解答器，当前采用了深度搜索，若使用广度搜索也可以的
 * @Author xxd
 * @Date 2021/10/22
 * @Version 1.0
 */
public class L0815串行的谜题解答器 {
    public class SequentialPuzzleSolver <P, M> {
        //puzzle理解为游戏引擎，做游戏规则判定工作，例如移动，判断周围可走位置等等
        private final Puzzle<P, M> puzzle;
        //seen用于判断哪些位置已经走过，记录走过的点集
        private final Set<P> seen = new HashSet<P>();

        public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
            this.puzzle = puzzle;
        }

        public List<M> solve() {
            P pos = puzzle.initialPosition();
            return search(new PuzzleNode<P, M>(pos, null, null));
        }

        private List<M> search(PuzzleNode<P, M> node) {
            if (!seen.contains(node.pos)) {
                seen.add(node.pos);
                if (puzzle.isGoal(node.pos))
                    return node.asMoveList();
                for (M move : puzzle.legalMoves(node.pos)) {
                    P pos = puzzle.move(node.pos, move);
                    PuzzleNode<P, M> child = new PuzzleNode<P, M>(pos, move, node);
                    List<M> result = search(child);
                    if (result != null)
                        return result;
                }
            }
            return null;
        }
    }
}
