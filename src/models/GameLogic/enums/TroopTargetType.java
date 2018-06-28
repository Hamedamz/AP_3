package models.GameLogic.enums;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Buildings.Storage;
import models.GameLogic.Entities.Buildings.Wall;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;

public enum TroopTargetType {
    BUILDING, RESOURCES, TOWER, WALL;

    public static boolean isTroopTargetAppropriate(TroopTargetType troopTargetType, Defender defender) {
        switch (troopTargetType) {
            case WALL:
                if (defender instanceof Wall) {
                    return true;
                }
                break;
            case TOWER:
                if (defender instanceof DefensiveBuilding) {
                    return true;
                }
                break;
            case RESOURCES:
                if(defender instanceof Storage) {
                    return true;
                }
                break;
            case BUILDING:
                if (!(defender instanceof Wall)) {
                    return true;
                }
                break;
        }
        return false;
    }
}
