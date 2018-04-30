package models.GameLogic.Entities.Troop;

import models.GameLogic.Position;
import models.GameLogic.enums.TroopTargetType;

public class Archer extends AttackerTroop {

    public Archer(Position position, int campNumber) {
        super(position, campNumber);
        this.targetType = TroopTargetType.BUILDING;
    }

}
