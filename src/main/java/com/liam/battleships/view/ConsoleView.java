package com.liam.battleships.view;

import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.board.CellState;
import com.liam.battleships.model.board.Coordinate;

import java.util.Scanner;

public class ConsoleView implements GameView {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayBoard(Board board, boolean hideShips) {
        int size = board.getSize();

        // column headers
        System.out.print("  ");
        for (int x = 0; x < size; x++) {
            System.out.print(x + " ");
        }
        System.out.println();

        for (int y = 0; y < size; y++) {
            // row headers
            System.out.print(y + " ");

            for (int x = 0; x < size; x++) {
                CellState state = board.getCellState(new Coordinate(x, y));
                char symbol = getSymbolForState(state, hideShips);
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public Coordinate promptForTarget(int boardSize) {
            System.out.println("Enter target coordinates (0-" + (boardSize - 1) + ")");

            int x = getValidInput("X: ", boardSize);
            int y = getValidInput("Y: ", boardSize);

            return new Coordinate(x, y);
    }

    private int getValidInput(String prompt, int maxBound) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= 0 && value < maxBound) {
                    return value;
                } else  {
                    System.out.println("Coordinate must be between 1 and " + (maxBound - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter whole numbers only.");
            }
        }
    }

    private char getSymbolForState(CellState state, boolean hideShips) {
        return switch (state) {
            case WATER -> '~';
            case SHIP -> hideShips ? '~' : 'S'; // hides ships if it's the enemy board
            case HIT -> 'X';
            case MISS -> 'O';
            case SUNK -> '#';
        };
    }
}
