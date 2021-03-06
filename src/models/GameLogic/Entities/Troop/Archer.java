package models.GameLogic.Entities.Troop;

import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.GameLogic.enums.TroopTargetType;

public class Archer extends AttackerTroop {

    public Archer() {
        this.targetType = TroopTargetType.BUILDING;
        this.moveType = MoveType.GROUND;
    }
}
