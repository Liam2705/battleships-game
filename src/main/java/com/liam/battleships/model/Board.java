package com.liam.battleships.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int DEFAULT_SIZE = 10;

    private final int size;
    private final CellState[][] grid;
    private final List<Ship> ships;

    public Board() {
        this(DEFAULT_SIZE); // constructor chaining
    }

    public Board(int size) {
        this.size = size;
        this.grid = new CellState[size][size];
        this.ships = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                grid[y][x] = CellState.WATER;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public CellState getCellState(Coordinate coord) {
        if (!isValidCoordinate(coord)) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        return grid[coord.y()][coord.x()];

    }

    public boolean isValidCoordinate(Coordinate coord) {
        return coord.x() >= 0 && coord.x() < size && coord.y() >= 0 && coord.y() < size;
    }

    public boolean placeShip(Ship ship, Coordinate start, boolean isHorizontal) {
        List<Coordinate> targetCoords = new ArrayList<>();

        for (int i = 0; i <= ship.getLength(); i++) {
            int x =  start.x() + (isHorizontal ? i : 0);
            int y = start.y() + (isHorizontal ? 0 : i);
            Coordinate current = new Coordinate(x, y);

            // checks boundaries and collisions
            if (!isValidCoordinate(current) || grid[current.y()][current.x()] != CellState.WATER) {
                return false;
            }

            targetCoords.add(current);
        }
        ship.setCoordinates(targetCoords);
        ships.add(ship);

        for (Coordinate c : targetCoords) {
            grid[c.y()][c.x()] = CellState.SHIP;
        }
        return true;
    }

    public AttackStatus recieveAttack(Coordinate target) {
        if (!isValidCoordinate(target)) {
            return AttackStatus.INVALID;
        }

        CellState currentState = grid[target.y()][target.x()];

        if (currentState == CellState.HIT || currentState == CellState.SUNK ||
                currentState == CellState.MISS) {
            return AttackStatus.ALREADY_ATTACKED;
        }

        if (currentState == CellState.WATER) {
            grid[target.y()][target.x()] = CellState.MISS;
            return AttackStatus.MISS;
        }

        if (currentState == CellState.SHIP) {
            Ship hitShip = getShipAt(target);
            if (hitShip != null) {
                hitShip.registerHit();
                grid[target.y()][target.x()] = CellState.HIT;

                if (hitShip.isSunk()) {
                    markShipAsSunk(hitShip);
                    return AttackStatus.SUNK;
                }
                return AttackStatus.HIT;
            }
        }

        return AttackStatus.INVALID;
    }

    public boolean areAllShipsSunk(){
        if (ships.isEmpty()) return false;

        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    private Ship getShipAt(Coordinate coord) {
        for (Ship ship : ships) {
            for (Coordinate c : ship.getCoordinates()) {
                if (c.x() == coord.x() && c.y() == coord.y()) {
                    return ship;
                }
            }
        }
        return null;
    }

    private void markShipAsSunk(Ship ship) {
        for (Coordinate c : ship.getCoordinates()) {
            grid[c.y()][c.x()] = CellState.SUNK;
        }
    }

}
