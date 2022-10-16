package Greedy;

import aima.search.framework.GoalTest;

public class Greedy_GoalTest implements GoalTest {

    public boolean isGoalState(Object aState) {

        GreedyBoard board= (GreedyBoard) aState;
        return board.isGoal();
    }
}
