package models.GameLogic.Entities.Troop;

import models.GameLogic.Position;
import models.GameLogic.enums.TroopTargetType;

public class Dragon extends AttackerTroop {

    public Dragon() {
        this.targetType = TroopTargetType.BUILDING;
    }

    @Override
    public void upgrade() {
        // TODO: 5/5/2018  
    }
}
