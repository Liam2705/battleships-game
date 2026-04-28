package com.liam.battleships.view;

import com.liam.battleships.controller.GameController;
import com.liam.battleships.model.board.Board;
import com.liam.battleships.model.board.CellState;
import com.liam.battleships.model.board.Coordinate;

import java.util.Scanner;

public class ConsoleView implements GameView {
    private final Scanner scanner = new Scanner(System.in);
    private GameController controller;
    private Board lastKnownEnemyBoard;

    @Override
    public void setController(GameController controller) {
        this.controller = controller;
        startInputListenerThread();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayBoard(Board board, boolean hideShips) {
        System.out.println("\n--- YOUR FLEET ---");
        printGrid(board, hideShips);
    }

    @Override
    public void displayEnemyBoard(Board board) {
        this.lastKnownEnemyBoard = board;
        System.out.println("\n--- ENEMY WATERS ---");
        printGrid(board, true);
    }

    private void printGrid(Board board, boolean hideShips) {
        int size = board.getSize();
        // column headers
        System.out.print("  ");
        for (int x = 0; x < size; x++) System.out.print(x + " ");
        System.out.println();

        for (int y = 0; y < size; y++) {
            // row headers
            System.out.print(y + " ");
            for (int x = 0; x < size; x++) {
                CellState state = board.getCellState(new Coordinate(x, y));
                System.out.print(getSymbolForState(state, hideShips) + " ");
            }
            System.out.println();
        }
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

    private void startInputListenerThread() {
        Thread inputThread = new Thread(() -> {
            while (true) {
                if (lastKnownEnemyBoard != null) {
                    try {
                        System.out.print("Enter target X and Y (e.g., '3 4'): ");

                        String input = scanner.nextLine().trim();
                        if (input.isEmpty()) continue;

                        String[] parts = input.split("\\s+");
                        if (parts.length == 2) {
                            int x = Integer.parseInt(parts[0]);
                            int y = Integer.parseInt(parts[1]);

                            // Trigger the event in the Controller
                            controller.executeHumanTurn((new Coordinate(x, y)));
                        } else {
                            System.out.println("Please enter two numbers separated by a space.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter numbers only.");
                    }
                } else {
                    try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                }
            }
        });

        // Setting as a daemon thread so it closes automatically when the GUI window is closed
        inputThread.setDaemon(true);
        inputThread.start();
    }
}
