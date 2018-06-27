package models.GameLogic.Entities.Troop;

import models.GameLogic.enums.MoveType;
import models.GameLogic.enums.TroopTargetType;

public class Dragon extends AttackerTroop {

    public Dragon() {
        this.moveType = MoveType.AIR;
        this.targetType = TroopTargetType.BUILDING;
    }

}
