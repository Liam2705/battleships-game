package com.liam.battleships.model.ship;

import java.util.ArrayList;
import java.util.List;

public class QuickMatchShipFactory implements ShipFactory {
    @Override
    public List<Ship> createFleet() {
        List<Ship> quickFleet = new ArrayList<>();

        quickFleet.add(new Carrier());
        quickFleet.add(new Destroyer());
        quickFleet.add(new Destroyer());

        return quickFleet;
    }
}
