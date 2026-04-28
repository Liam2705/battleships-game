package com.liam.battleships;

import com.liam.battleships.controller.GameController;
import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.player.AIPlayer;
import com.liam.battleships.model.player.HumanPlayer;
import com.liam.battleships.model.ship.ClassicShipFactory;
import com.liam.battleships.model.ship.ShipFactory;
import com.liam.battleships.strategy.HuntTargetStrategy;
import com.liam.battleships.utils.AutoPlacer;
import com.liam.battleships.view.ConsoleView;
import com.liam.battleships.view.GameView;
import com.liam.battleships.view.GuiView;


public class Game {
    void main(){
        ShipFactory factory = new ClassicShipFactory();

        Board humanBoard = new Board();
        Board aiBoard = new Board();

        AutoPlacer.placeFleetRandomly(humanBoard, factory.createStandardFleet());
        AutoPlacer.placeFleetRandomly(aiBoard, factory.createStandardFleet());

        HumanPlayer human = new HumanPlayer("Liam", humanBoard);
        AIPlayer ai = new AIPlayer("Computer", aiBoard, new HuntTargetStrategy());

        GameController controller = new GameController(human, ai);

        GameView consoleView = new ConsoleView();
        GameView GuiView = new GuiView();

        controller.addView(consoleView);
        controller.addView(GuiView);

        controller.startGame();
    }
}
