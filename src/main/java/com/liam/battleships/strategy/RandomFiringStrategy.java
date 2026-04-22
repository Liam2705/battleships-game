package com.liam.battleships.strategy;

import com.liam.battleships.model.Board;
import com.liam.battleships.model.CellState;
import com.liam.battleships.model.Coordinate;
import java.util.Random;

/*
* Easy AI firing strategy: Fires randomly at any valid cells that
* haven't already been attacked
*/
public class RandomFiringStrategy implements AIFiringStrategy {
    private final Random random = new Random();

    @Override
    public Coordinate determineNextShot(Board enemyBoard) {
        int size = enemyBoard.getSize();
        Coordinate target;

        do {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            target = new Coordinate(x, y);
        } while (enemyBoard.getCellState(target) == CellState.HIT ||
                 enemyBoard.getCellState(target) == CellState.MISS ||
                 enemyBoard.getCellState(target) == CellState.SUNK );

        return target;
    }
}
