package com.liam.battleships.model.ship;

import java.util.ArrayList;
import java.util.List;

public class ClassicShipFactory implements ShipFactory {
    @Override
    public Ship createCarrier() { return new Carrier(); }
    @Override
    public Ship createBattleship() { return new Battleship(); }
    @Override
    public Ship createCruiser() { return new Cruiser(); }
    @Override
    public Ship createSubmarine() { return new Submarine(); }
    @Override
    public Ship createDestroyer() { return new Destroyer(); }

    @Override
    public List<Ship> createStandardFleet() {
        List<Ship> fleet = new ArrayList<>();
        fleet.add(createCarrier());
        fleet.add(createBattleship());
        fleet.add(createCruiser());
        fleet.add(createSubmarine());
        fleet.add(createDestroyer());
        return fleet;
    }
}
