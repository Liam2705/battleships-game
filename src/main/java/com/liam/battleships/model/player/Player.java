package com.liam.battleships.model.player;

import com.liam.battleships.model.board.Board;

public abstract class Player {
    protected final String name;
    protected final Board board;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }

    public boolean hasLost() {
        return board.areAllShipsSunk();
    }
}
