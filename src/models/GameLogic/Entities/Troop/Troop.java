package models.GameLogic.Entities.Troop;

import interfaces.Movable;
import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.Setting.GameLogicConfig;

public abstract class Troop extends Entity implements Movable {
    private transient Camp troopCamp;
    protected MoveType moveType;
    protected int speed;
    private Position movementTarget;

    public Troop() {
        super();
                String className = this.getClass().getName();
        this.speed = (Integer) GameLogicConfig.getFromDictionary(className + "Speed");
    }

    public void setTroopCamp(Camp troopCamp) {
        this.troopCamp = troopCamp;
    }
}


//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}