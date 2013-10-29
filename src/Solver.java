/**
 * 
 * @author Huangzf
 *
 */
public class Solver {
    
    /*
     * Since each search node records the previous search node to get there, 
     * you can chase the pointers all the way back to the initial search node 
     * (and consider them in reverse order)
     */
    private SearchNode gameTree;
    
    private class SearchNode implements Comparable<SearchNode> {

        private int moves;
        private int priority;
        private SearchNode parent;
        private Board board;
        
        public SearchNode(Board board, SearchNode parent) {
            this.board = board;
            this.parent = parent;
            if (parent != null) this.moves = parent.moves + 1;
            this.priority = board.manhattan() + this.moves;
            
            /*
             *  A* algorithms;
             *  the priorities of the search nodes dequeued 
             *  from the priority queue never decrease
             */
            assert null == parent || parent.priority <= this.priority;
        }
        
        public int compareTo(SearchNode o) {
            return this.priority - o.priority;
        }
        
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial.isGoal())
            gameTree = new SearchNode(initial, null);
        else {
            MinPQ<SearchNode> mainPQ = new MinPQ<SearchNode>();
            MinPQ<SearchNode> checkPQ = new MinPQ<SearchNode>();
            mainPQ.insert(new SearchNode(initial, null));
            checkPQ.insert(new SearchNode(initial.twin(), null));
            while (true) {
                gameTree = find(mainPQ);
                // StdOut.println(gameTree.board.toString());
                if (gameTree.board.isGoal()) break;
                if (find(checkPQ).board.isGoal()) {
                    gameTree = null;
                    break;
                }
            }
        }
    }
    
    private SearchNode find(MinPQ<SearchNode> minPQ) {
        SearchNode node = minPQ.delMin();
        for (Board board : node.board.neighbors())
            if (null == node.parent || !board.equals(node.parent.board))
                minPQ.insert(new SearchNode(board, node));
        return node;
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return null != gameTree;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (null == gameTree) return -1;
        return gameTree.moves;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (null == gameTree) return null;
        Stack<Board> stack = new Stack<Board>();
        SearchNode node = gameTree;
        while (node != null) {
            stack.push(node.board);
            node = node.parent;
        }
        return stack;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
