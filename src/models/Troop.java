package models;

import interfaces.Destroyable;
import interfaces.Movable;
import interfaces.MovingAttacker;
import models.enums.TroopTargetType;
import models.enums.TroopType;

public abstract class Troop extends Entity implements Movable {
    protected int campNumber;
    protected TroopType troopType;
    protected int speed;

}


//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}