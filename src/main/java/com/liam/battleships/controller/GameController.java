package com.liam.battleships.controller;

import com.liam.battleships.model.board.AttackStatus;
import com.liam.battleships.model.board.Coordinate;
import com.liam.battleships.model.player.AIPlayer;
import com.liam.battleships.model.player.HumanPlayer;
import com.liam.battleships.view.GameView;

/*
* Manages the main game loop, controls the switching
* between player and AI turns
*/
public class GameController {
    private final HumanPlayer human;
    private final AIPlayer ai;
    private final GameView view;

    public GameController(HumanPlayer human, AIPlayer ai, GameView view) {
        this.human = human;
        this.ai = ai;
        this.view = view;
    }

    public void startGame() {
        view.showMessage("--- Welcome to BattleShips! ---");

        boolean isHumanTurn = true;
        boolean gameOver = false;
        while (!gameOver) {
            if (isHumanTurn) {
                gameOver = executeHumanTurn();
            } else {
                gameOver = executeAITurn();
            }

            isHumanTurn = !isHumanTurn; // swaps turns
        }
    }

    public boolean executeHumanTurn() {
        view.showMessage("--- YOUR TURN ---");

        view.showMessage("Enemy Waters:");
        view.displayBoard(ai.getBoard(), true);

        boolean validTurn = false;

        while (!validTurn) {
            Coordinate target = view.promptForTarget(ai.getBoard().getSize());
            AttackStatus result = ai.getBoard().recieveAttack(target);

            validTurn = processAttackResult(result, target, human.getName());
        }

        return ai.hasLost();
    }

    public boolean executeAITurn() {
        view.showMessage("--- ENEMY TURN ---");

        boolean validTurn = false;

        while (!validTurn) {
            Coordinate target = ai.calculateNextMove(human.getBoard());
            AttackStatus result = human.getBoard().recieveAttack(target);

            validTurn = processAttackResult(result, target, ai.getName());
        }

        view.showMessage("Your Fleet Status:");
        view.displayBoard(human.getBoard(), false);

        return human.hasLost();
    }

    /**
     * Converts the AttackStatus into a readable console message
     * and determines if the turn was valid
     */
    private boolean processAttackResult(AttackStatus result, Coordinate target, String attackerName) {
        switch (result) {
            case MISS:
                view.showMessage(attackerName + " fired at " + formatCoord(target) + " and MISSED.");
                return true;
            case HIT:
                view.showMessage("BOOM! " + attackerName + " scored a HIT at " + formatCoord(target) + "!");
                return true;
            case SUNK:
                view.showMessage("DEVASTATING! " + attackerName + " SUNK a ship at " + formatCoord(target) + "!");
                return true;
            case ALREADY_ATTACKED:
                if (attackerName.equals(human.getName())) {
                    view.showMessage("You already fired there! Try a different coordinate.");
                }
                return false;
            case INVALID:
                if (attackerName.equals(human.getName())) {
                    view.showMessage("Invalid coordinate! Out of bounds.");
                }
                return false;
            default:
                return false;
        }
    }

    private String formatCoord(Coordinate c) {
        return "(" + c.x() + ", " + c.y() + ")";
    }
}
