package 第08章_线程池的使用;

import java.util.Set;

/**
 * @Description Puzzle
 * @Author xxd
 * @Date 2021/10/22
 * @Version 1.0
 */
public interface Puzzle<P, M> {
    P initialPosition();

    boolean isGoal(P position);

    Set<M> legalMoves(P position);

    P move(P position, M move);
}
