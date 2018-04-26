package models.GameLogic;

import interfaces.Movable;
import models.GameLogic.enums.MoveType;

public abstract class Troop extends Entity implements Movable {
    protected transient Camp troopCamp;
    protected int campNumber;
    protected MoveType moveType;
    protected int speed;

}


//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}