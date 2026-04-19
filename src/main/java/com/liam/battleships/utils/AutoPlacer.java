package com.liam.battleships.utils;

import com.liam.battleships.model.Board;
import com.liam.battleships.model.Coordinate;
import com.liam.battleships.model.Ship;

import java.util.List;
import java.util.Random;

public class AutoPlacer {
    private static final Random random = new Random();

    public static void placeFleetRandomly(Board board, List<Ship> fleet) {
        int size = board.getSize();

        for (Ship ship : fleet) {
            boolean placed = false;
            while (!placed) {
                int x = random.nextInt(size);
                int y = random.nextInt(size);
                boolean isHorizontal = random.nextBoolean();

                Coordinate start = new Coordinate(x, y);

                placed = board.placeShip(ship, start, isHorizontal);

            }
        }

    }

}
