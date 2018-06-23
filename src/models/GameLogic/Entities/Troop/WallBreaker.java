package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Buildings.Wall;
import models.GameLogic.enums.MoveType;
import models.GameLogic.enums.TroopTargetType;

public class WallBreaker extends AttackerTroop {

    boolean isThereWall = true;

    public WallBreaker() {
        moveType = MoveType.GROUND;
        targetType = TroopTargetType.WALL;
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        super.giveDamageTo(destroyable, battleGround);
        if(destroyable instanceof Wall) {
            hitPoints = -10000;
        }
        else if(isThereWall) {
            isThereWall = false;
            damage /= 4;
        }
    }
}
