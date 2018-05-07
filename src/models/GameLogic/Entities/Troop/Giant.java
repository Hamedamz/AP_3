package models.GameLogic.Entities.Troop;

import models.GameLogic.Entities.Entity;
import models.GameLogic.Map;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.GameLogic.enums.TroopTargetType;

public class Giant extends AttackerTroop {

    public Giant() {
        this.targetType = TroopTargetType.RESOURCES;
    }

}
