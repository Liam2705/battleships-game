package com.liam.battleships;

import com.liam.battleships.controller.GameController;
import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.player.AIPlayer;
import com.liam.battleships.model.player.HumanPlayer;
import com.liam.battleships.model.ship.ClassicShipFactory;
import com.liam.battleships.model.ship.Ship;
import com.liam.battleships.model.ship.ShipFactory;
import com.liam.battleships.strategy.RandomFiringStrategy;
import com.liam.battleships.utils.AutoPlacer;
import com.liam.battleships.view.ConsoleView;
import com.liam.battleships.view.GameView;

import java.util.List;

public class Game {
    void main(){
        ShipFactory factory = new ClassicShipFactory();

        Board humanBoard = new Board();
        Board aiBoard = new Board();

        AutoPlacer.placeFleetRandomly(humanBoard, factory.createStandardFleet());
        AutoPlacer.placeFleetRandomly(aiBoard, factory.createStandardFleet());

        HumanPlayer human = new HumanPlayer("Liam", humanBoard);
        AIPlayer ai = new AIPlayer("Computer", aiBoard, new RandomFiringStrategy());

        GameView view = new ConsoleView();
        GameController controller = new GameController(human, ai, view);

        controller.startGame();

        if (human.hasLost()) {
            view.showMessage("\nDEFEAT! All your ships have been destroyed.");
        } else {
            view.showMessage("\nVICTORY! You have sunk the enemy fleet.");
        }
    }
}
