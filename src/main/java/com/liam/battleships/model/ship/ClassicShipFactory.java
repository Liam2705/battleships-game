package com.liam.battleships.model.ship;

import java.util.ArrayList;
import java.util.List;

public class ClassicShipFactory implements ShipFactory {
    @Override
    public List<Ship> createFleet() {
        List<Ship> standardFleet = new ArrayList<>();
        standardFleet.add(new Carrier());
        standardFleet.add(new Battleship());
        standardFleet.add(new Cruiser());
        standardFleet.add(new Submarine());
        standardFleet.add(new Destroyer());
        return standardFleet;
    }
}
