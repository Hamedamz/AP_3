package models.GameLogic.Entities.Troop;

import models.GameLogic.enums.MoveType;
import models.GameLogic.enums.TroopTargetType;

public class Guardian extends AttackerTroop {

    public Guardian() {
        this.targetType = TroopTargetType.BUILDING;
        this.moveType = MoveType.GROUND;
    }

}
