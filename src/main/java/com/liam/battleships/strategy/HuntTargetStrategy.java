package com.liam.battleships.strategy;

import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.board.CellState;
import com.liam.battleships.model.board.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Medium AI firing strategy: Fires randomly until it hits a ship,
 * then beigns targeting adjacent coordinates to sink the ship.
 */
public class HuntTargetStrategy implements AIFiringStrategy {
    private final Random random = new Random();
    private final List<Coordinate> targetQueue = new ArrayList<>();
    private Coordinate lastFiredCoordinate =  null;


    @Override
    public Coordinate determineNextShot(Board enemyBoard) {
        updateStateBasedOnLastShot(enemyBoard);

        Coordinate target;

        // TARGET MODE - Uses the queue of adjacent coordinates to target ships
        if (!targetQueue.isEmpty()) {
            target = getNextValidTarget(enemyBoard);
            if (target != null) {
                lastFiredCoordinate = target;
                return target;
            }
        }

        // Fire randomly at un-guessed water until a hit is detected
        int size = enemyBoard.getSize();
        do {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            target = new Coordinate(x, y);
        } while (isAlreadyGuessed(enemyBoard, target));

        lastFiredCoordinate = target;
        return target;
    }

    /**
     * Checks the board to see if the last shot was a HIT. Then, if it
     * was add its adjacent coordinates to the target queue.
     */
    private void updateStateBasedOnLastShot(Board enemyBoard) {
        if (lastFiredCoordinate == null) return;

        CellState lastResult = enemyBoard.getCellState(lastFiredCoordinate);

        if (lastResult == CellState.HIT) {
            int x = lastFiredCoordinate.x();
            int y = lastFiredCoordinate.y();

            // Queues the next targets as NORTH, SOUTH, WEST, and EAST
            queuePotentialTarget(new Coordinate(x, y - 1), enemyBoard); // North
            queuePotentialTarget(new Coordinate(x, y + 1), enemyBoard); // South
            queuePotentialTarget(new Coordinate(x + 1, y), enemyBoard); // East
            queuePotentialTarget(new Coordinate(x - 1, y), enemyBoard); // West
        } else if (lastResult == CellState.SUNK) {
            targetQueue.clear();
        }
    }

    /**
     * Adds an adjacent coordinate to the queue if it within bounds
     * and hasn't been fired at yet.
     */
    private void queuePotentialTarget(Coordinate coord, Board enemyBoard) {
        if (enemyBoard.isValidCoordinate(coord) && !isAlreadyGuessed(enemyBoard, coord)) {
            if (!targetQueue.contains(coord)) {
                targetQueue.add(coord);
            }
        }
    }

    /**
     * Pops coordinates off the target queue until it finds a valid one.
     */
    private Coordinate getNextValidTarget(Board enemyBoard) {
        while (!targetQueue.isEmpty()) {
            Coordinate potentialTarget = targetQueue.removeLast();

            if (!isAlreadyGuessed(enemyBoard, potentialTarget)) {
                return potentialTarget;
            }
        }
        return null;
    }



    private boolean isAlreadyGuessed(Board board, Coordinate target) {
        CellState state = board.getCellState(target);
        return state == CellState.HIT || state == CellState.MISS || state == CellState.SUNK;
    }
}
