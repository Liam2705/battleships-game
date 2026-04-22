package com.liam.battleships.strategy;

import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.board.Coordinate;

/*
* Strategy Interface that determines the AI's next move
* Allows for multiple difficulties to be added to the game
* with different ways of playing against the player
*/
public interface AIFiringStrategy {
    public Coordinate determineNextShot(Board enemyBoard);
}
