package models.GameLogic.Entities.Troop;

import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.GameLogic.enums.TroopTargetType;

public class Dragon extends AttackerTroop {

    public Dragon() {
        this.moveType = MoveType.Air;
        this.targetType = TroopTargetType.BUILDING;
    }

}
