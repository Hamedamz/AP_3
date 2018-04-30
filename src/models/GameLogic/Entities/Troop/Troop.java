package models.GameLogic.Entities.Troop;

import interfaces.Movable;
import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.Setting.GameLogicConfig;

public abstract class Troop extends Entity implements Movable {
    protected transient Camp troopCamp;
    protected int campNumber;
    protected MoveType moveType;
    protected int speed;

    public Troop(Position position, int campNumber) {
        super(position);
        this.campNumber = campNumber;
        String className = this.getClass().getName();
        this.speed = (Integer) GameLogicConfig.getFromDictionary(className + "Speed");
    }
}


//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}