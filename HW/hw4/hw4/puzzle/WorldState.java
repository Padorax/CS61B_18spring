package hw4.puzzle;

public interface WorldState {
    /** Provides an estimate of the number of moves to reach the goal.
     * Must be less than or equal to the correct distance. */
    int estimatedDistanceToGoal();//must be 0 at goal(<=0)

    /** Provides an iterable of all the neighbors of this WorldState. */
    //can think of this as G.adj(v) i.e, find all the nbr of v(linked by an edge)
    Iterable<WorldState> neighbors();

    default boolean isGoal() {
        return estimatedDistanceToGoal() == 0;
    }
}
