package models.GameLogic.Entities.Troop;

import models.interfaces.Destroyable;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Wall;
import models.GameLogic.enums.MoveType;
import models.GameLogic.enums.TroopTargetType;
import viewers.BattleGroundScene;

public class WallBreaker extends AttackerTroop {

    boolean isThereWall = true;

    public WallBreaker() {
        moveType = MoveType.GROUND;
        targetType = TroopTargetType.WALL;
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        int size = (destroyable instanceof Building) ? ((Building) destroyable).getSize() : 1;
        if (getPosition().calculateDistanceFromBuilding(destroyable.getPosition(), size) <= getEffectRange()) {
            attackCounter = 0;
            destroyable.takeDamageFromAttack(damage);
            if(destroyable instanceof Wall) {
                hitPoints = -10000;
            }
            else if(isThereWall) {
                isThereWall = false;
                damage /= 4;
            }
            BattleGroundScene.getInstance().attackHappened(this, destroyable);
        }
    }
}
