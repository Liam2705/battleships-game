package com.liam.battleships.model;

import java.util.List;

public interface ShipFactory {
    Ship createCarrier();
    Ship createBattleship();
    Ship createCruiser();
    Ship createSubmarine();
    Ship createDestroyer();

    List<Ship> createStandardFleet();
}
