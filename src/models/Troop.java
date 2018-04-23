package models;

import interfaces.Movable;
import models.enums.MoveType;

public abstract class Troop extends Entity implements Movable {
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