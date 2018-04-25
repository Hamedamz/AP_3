package models.GameLogic;

import interfaces.Movable;
import models.GameLogic.enums.MoveType;

public abstract class Troop extends Entity implements Movable {
    protected Camp troopCamp;
    protected MoveType moveType;
    protected int speed;

}


//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}