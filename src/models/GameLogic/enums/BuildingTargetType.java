package models.GameLogic.enums;

import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Troop.*;

public enum BuildingTargetType {
    GROUND_AND_AIR, GROUND, AIR;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }

    public static boolean isBuildingTargetAppropriate(DefensiveBuilding defensiveBuilding, AttackerTroop troop) {
        switch (defensiveBuilding.getTargetType()) {
            case GROUND:
                if (troop.getTroopMoveType().equals(MoveType.GROUND) || troop.getTroopMoveType().equals(MoveType.JUMPER)) {
                    return true;
                }
                break;
            case AIR:
                if (troop.getTroopMoveType().equals(MoveType.AIR)) {
                    return true;
                }
                break;
            case GROUND_AND_AIR:
                return true;
        }
        return false;
    }
}
