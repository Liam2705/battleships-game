package com.liam.battleships.view;

import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.board.Coordinate;

public interface GameView {
    void showMessage(String message);

    /**
     * Renders the board
     * @param hideShips If true, acts as the board to track enemy by hiding ships
     *                  that haven't been hit
     */
    void displayBoard(Board board, boolean hideShips);

    Coordinate promptForTarget(int boardSize);
}
