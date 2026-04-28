package com.liam.battleships.controller;

import com.liam.battleships.model.board.AttackStatus;
import com.liam.battleships.model.board.Coordinate;
import com.liam.battleships.model.player.AIPlayer;
import com.liam.battleships.model.player.HumanPlayer;
import com.liam.battleships.view.GameView;

import java.util.ArrayList;
import java.util.List;

/*
* Manages the main game loop, controls the switching
* between player and AI turns
*/
public class GameController {
    private final HumanPlayer human;
    private final AIPlayer ai;

    // a list of subscribed view for the observer pattern
    private final List<GameView> views = new ArrayList<>();
    private boolean gameOver = false;

    public GameController(HumanPlayer human, AIPlayer ai) {
        this.human = human;
        this.ai = ai;
    }

    /**
     * Subscribes a new View to this Controller.
     */
    public void addView(GameView view) {
        views.add(view);
        view.setController(this);
    }

    public void startGame() {
        broadcastMessage("--- Welcome to BattleShips! ---");
        updateAllViews();
    }

    public void executeHumanTurn(Coordinate target) {
        if (gameOver) return;

        AttackStatus humanResult = ai.getBoard().receiveAttack(target);

        if (humanResult == AttackStatus.INVALID || humanResult == AttackStatus.ALREADY_ATTACKED) {
            broadcastMessage("Invalid or already attacked coordinate. Try again.");
            return;
        }

        processAttackResult(humanResult, target, human.getName());
        updateAllViews();

        if (ai.hasLost()) {
            endGame(human.getName());
            return;
        }

        executeAITurn();
    }

    public void executeAITurn() {
        boolean validTurn = false;

        while (!validTurn) {
            Coordinate target = ai.calculateNextMove(human.getBoard());
            AttackStatus result = human.getBoard().receiveAttack(target);

            validTurn = processAttackResult(result, target, ai.getName());
        }

        updateAllViews();

        if (human.hasLost()) {
            endGame(ai.getName());
        }
    }

    /**
     * Broadcasts board updates to EVERY registered view simultaneously.
     */
    private void updateAllViews() {
        for (GameView view : views) {
            view.displayBoard(human.getBoard(), false);
            view.displayEnemyBoard(ai.getBoard());
        }
    }

    /**
     * Broadcasts text messages to all registered views simultaneously.
     */
    private void broadcastMessage(String message) {
        for (GameView view : views) {
            view.showMessage(message);
        }
    }

    /**
     * Converts the AttackStatus into a readable console message
     * and determines if the turn was valid
     */
    private boolean processAttackResult(AttackStatus result, Coordinate target, String attackerName) {
        switch (result) {
            case MISS:
                broadcastMessage(attackerName + " fired at " + formatCoord(target) + " and MISSED.");
                return true;
            case HIT:
                broadcastMessage("BOOM! " + attackerName + " scored a HIT at " + formatCoord(target) + "!");
                return true;
            case SUNK:
                broadcastMessage("DEVASTATING! " + attackerName + " SUNK a ship at " + formatCoord(target) + "!");
                return true;
            case ALREADY_ATTACKED:
                if (attackerName.equals(human.getName())) {
                    broadcastMessage("You already fired there! Try a different coordinate.");
                }
                return false;
            case INVALID:
                if (attackerName.equals(human.getName())) {
                    broadcastMessage("Invalid coordinate! Out of bounds.");
                }
                return false;
            default:
                return false;
        }
    }

    private String formatCoord(Coordinate c) {
        return "(" + c.x() + ", " + c.y() + ")";
    }

    private void endGame(String winnerName) {
        gameOver = true;
        broadcastMessage("GAME OVER! " + winnerName + " wins the battle!");
    }
}
