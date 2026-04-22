package com.liam.battleships;

import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.ship.ClassicShipFactory;
import com.liam.battleships.model.ship.Ship;
import com.liam.battleships.utils.AutoPlacer;
import com.liam.battleships.view.ConsoleView;
import com.liam.battleships.view.GameView;

import java.util.List;

public class Game {
    void main(){
        Board testBoard = new Board();
        ClassicShipFactory factory = new ClassicShipFactory();
        List<Ship> fleet = factory.createStandardFleet();

        AutoPlacer.placeFleetRandomly(testBoard, fleet);

        GameView view = new ConsoleView();

        view.showMessage("--- MY BOARD (Ships Visible) ---");
        view.displayBoard(testBoard, false);

        view.showMessage("--- ENEMY VIEW (Ships Hidden) ---");
        view.displayBoard(testBoard, true);
        view.promptForTarget(testBoard.getSize());
    }
}
