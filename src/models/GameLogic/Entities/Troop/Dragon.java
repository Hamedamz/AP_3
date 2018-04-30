package models.GameLogic.Entities.Troop;

import models.GameLogic.Position;
import models.GameLogic.enums.TroopTargetType;

public class Dragon extends AttackerTroop {

    public Dragon(Position position, int campNumber) {
        super(position, campNumber);
        this.targetType = TroopTargetType.BUILDING;
    }
}
