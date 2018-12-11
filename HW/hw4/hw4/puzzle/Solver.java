package hw4.puzzle;
import java.lang.invoke.WrongMethodTypeException;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private MinPQ<searchNode> BMS;
    private int move;
    private searchNode target;

    private class searchNode {
        public searchNode prev;
        public int move;
        public WorldState state;
        public int priority;//distTo(including h(v))

        public searchNode(WorldState state, int moveNum,searchNode prev) {
            this.prev = prev;
            move = moveNum;
            this.state = state;
        }
    }

    private class nodeComparator implements Comparator<searchNode> {
        @Override
        public int compare(searchNode n1, searchNode n2) {
            //use move/distance(distTo(src,v) + h(v) as priority
            return n1.priority - n2.priority;
        }
    }
    private void init(WorldState initial) {
        nodeComparator com = new nodeComparator();
        //default PQ size: 1
        BMS = new MinPQ<>(1, com);
        searchNode initNode = new searchNode(initial, 0, null);
        BMS.insert(initNode);
    }
    //return false if a node goes back
    private boolean shouldAdd(searchNode node) {
        searchNode n = node.prev;
        while(n != null) {
            if (n.state.equals(node.state)) {
                return false;
            }
            n = n.prev;
        }
        return true;
    }

    /** Constructor which solves the puzzle, computing everything necessary for moves()
    and solution() to not have to solve the problem again. Solves the puzzle using
    the A* algorithm. Assumes a solution exists.*/
    public Solver(WorldState initial) {
        //insert initial search node
        init(initial);
        searchNode curpathNode = BMS.delMin();

        while (!curpathNode.state.isGoal()) {
            int dist = curpathNode.move;
            int prio = curpathNode.priority;
            for (WorldState nbr: curpathNode.state.neighbors()) {
                //check to avoid adding the node where you came from
                // do not check at the first node as it does not have prev.state
                if (curpathNode.prev != null && nbr.equals(curpathNode.prev.state)) continue;

                /**the right way of relax edge(coming from curpathNode.state.word) by updating distTo
                * if nodes not in PQ, insert; else:
                * get a smaller dist/priority, update
                * get a larger dist, continue(automatically prevents going back)--should also fix:
                 * donot add in deleted nodes
                 *
                 * check can be done by iterating all current nodes in PQ
                 */

                /**The below insert a new node everytime instead of updating existed node or continue
                 * I see the real prob: its undirected so its bidirection
                 * So we can either check if goback by looking at prev, or we first compare value, going
                 * back to grandparent will only add distTo and will not be updated
                 * OPT:
                 * 1.check no enqueued WorldState is its own grandparent
                 * 2.or never allow any WorldState to be enqueued twice
                 * 3.or check BMS.contains(nodeTobeEnqueued)---PQ doesn't support this operation, we need to iterate
                 * through all nodes in BMS and check .equals(node.state)
                 */
                int updateDistTo = nbr.estimatedDistanceToGoal() + prio + 1;
                searchNode relaxNode = new searchNode(nbr, dist + 1, curpathNode);
                relaxNode.priority = updateDistTo;
                if (shouldAdd(relaxNode)) {
                    BMS.insert(relaxNode);
                }
            }
            curpathNode = BMS.delMin();
        }
        move = curpathNode.move;
        target = curpathNode;
    }

    /** Returns the minimum number of moves to solve the puzzle starting
    at the initial WorldState.*/
    public int moves() {
        return move;
    }

    /**  Returns a sequence of WorldStates from the initial WorldState
     to the solution.*/
    public Iterable<WorldState> solution() {
        Stack<WorldState> sol = new Stack<>();
        /*will lose the starting word
        while(target.prev != null) {
        */
        while(target != null) {
            sol.push(target.state);
            target = target.prev;
        }
        return sol;
    }
}
