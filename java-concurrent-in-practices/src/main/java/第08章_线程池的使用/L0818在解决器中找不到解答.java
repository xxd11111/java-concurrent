package 第08章_线程池的使用;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description L0818在解决器中找不到解答
 * @Author xxd
 * @Date 2021/10/26
 * @Version 1.0
 */
public class L0818在解决器中找不到解答 {
    public class PuzzleSolver <P,M> extends ConcurrentPuzzleSolver<P, M> {
        PuzzleSolver(Puzzle<P, M> puzzle) {
            super(puzzle);
        }

        private final AtomicInteger taskCount = new AtomicInteger(0);

        protected Runnable newTask(P p, M m, PuzzleNode<P, M> n) {
            return new CountingSolverTask(p, m, n);
        }

        class CountingSolverTask extends SolverTask {
            CountingSolverTask(P pos, M move, PuzzleNode<P, M> prev) {
                super(pos, move, prev);
                taskCount.incrementAndGet();
            }

            public void run() {
                try {
                    super.run();
                } finally {
                    if (taskCount.decrementAndGet() == 0)
                        solution.setValue(null);
                }
            }
        }
    }

    //以下同8-16
    public class ConcurrentPuzzleSolver<P, M> {
        private final Puzzle<P, M> puzzle;
        private final ExecutorService exec;
        //用于记录已经搜索的位置，避免竞态条件
        private final ConcurrentMap<P, Boolean> seen;
        //solution用于通知所有线程是否解决问题
        protected final ValueLatch<PuzzleNode<P, M>> solution = new ValueLatch<PuzzleNode<P, M>>();

        public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle) {
            this.puzzle = puzzle;
            this.exec = initThreadPool();
            this.seen = new ConcurrentHashMap<P, Boolean>();
            if (exec instanceof ThreadPoolExecutor) {
                ThreadPoolExecutor tpe = (ThreadPoolExecutor) exec;
                tpe.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
            }
        }

        private ExecutorService initThreadPool() {
            return Executors.newCachedThreadPool();
        }

        public List<M> solve() throws InterruptedException {
            try {
                P p = puzzle.initialPosition();
                exec.execute(newTask(p, null, null));
                // block until solution found
                PuzzleNode<P, M> solnPuzzleNode = solution.getValue();
                return (solnPuzzleNode == null) ? null : solnPuzzleNode.asMoveList();
            } finally {
                exec.shutdown();
            }
        }

        protected Runnable newTask(P p, M m, PuzzleNode<P, M> n) {
            return new SolverTask(p, m, n);
        }

        protected class SolverTask extends PuzzleNode<P, M> implements Runnable {
            SolverTask(P pos, M move, PuzzleNode<P, M> prev) {
                super(pos, move, prev);
            }

            public void run() {
                //这步判断很有意思，如有两个同时进入if，第一次会将pos，true加入map中，第二次进入if会直接跳过
                if (solution.isSet()
                        || seen.putIfAbsent(pos, true) != null)
                    return; // already solved or seen this position
                if (puzzle.isGoal(pos))
                    solution.setValue(this);
                else
                    for (M m : puzzle.legalMoves(pos))
                        exec.execute(newTask(puzzle.move(pos, m), m, this));
            }
        }
    }
}
