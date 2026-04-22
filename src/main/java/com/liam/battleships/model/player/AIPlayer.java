package com.liam.battleships.model.player;

import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.board.Coordinate;
import com.liam.battleships.strategy.AIFiringStrategy;

public class AIPlayer extends Player {
    private AIFiringStrategy firingStrategy;

    public AIPlayer(String name, Board board, AIFiringStrategy firingStrategy) {
        super(name, board);
        this.firingStrategy = firingStrategy;
    }

    // allows changing of AI difficulty in runtime
    public void setFiringStrategy(AIFiringStrategy firingStrategy) {
        this.firingStrategy = firingStrategy;
    }

    public Coordinate calculateNextMove(Board enemyBoard) {
        return firingStrategy.determineNextShot(enemyBoard);
    }
}
