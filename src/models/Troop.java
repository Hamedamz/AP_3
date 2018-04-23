package models;

import interfaces.Movable;
import models.enums.MoveType;

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