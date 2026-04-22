package com.liam.battleships.model.ship;

import java.util.List;

public interface ShipFactory {
    Ship createCarrier();
    Ship createBattleship();
    Ship createCruiser();
    Ship createSubmarine();
    Ship createDestroyer();

    List<Ship> createStandardFleet();
}
