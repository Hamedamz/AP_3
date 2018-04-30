package models.GameLogic.Entities.Troop;

import models.GameLogic.Position;
import models.GameLogic.enums.TroopTargetType;

public class Giant extends AttackerTroop {

    public Giant(Position position, int campNumber) {
        super(position, campNumber);
        this.targetType = TroopTargetType.RESOURCES;
    }
}
