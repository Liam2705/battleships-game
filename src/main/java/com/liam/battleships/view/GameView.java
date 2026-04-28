package com.liam.battleships.view;

import com.liam.battleships.controller.GameController;
import com.liam.battleships.model.board.Board;

public interface GameView {
    void setController(GameController controller);
    void showMessage(String message);

    /**
     * Renders the board
     * @param hideShips If true, acts as the board to track enemy by hiding ships
     *                  that haven't been hit
     */
    void displayBoard(Board board, boolean hideShips);

    void displayEnemyBoard(Board board);
}
