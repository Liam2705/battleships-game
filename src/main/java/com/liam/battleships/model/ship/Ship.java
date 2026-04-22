package com.liam.battleships.model.ship;

import com.liam.battleships.model.board.Coordinate;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    protected int health;
    protected List<Coordinate> coordinates;

    public abstract String getName();
    public abstract int getLength();

    public Ship(){
        this.health = getLength();
        this.coordinates = new ArrayList<>();
    }

    public int getHealth() {
        return health;
    }

    public boolean isSunk(){
        return health <=0;
    }

    public void registerHit(){
        if(!isSunk()){
            health--;
        }
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        if (coordinates.size() != getLength()) {
            throw new IllegalArgumentException(
                    "Coordinate count (" + coordinates.size() +
                    ") does not match " + getName() + " length (" + getLength() + ")"
            );
        }
        this.coordinates = coordinates;
    }

}
