package models;

import models.enums.TroopTargetType;

public class Troop {
    protected Resource trainCost;
    protected int trainTime;
    protected int hitpoints;
    protected TroopTargetType targetType;
    protected int range;
    protected int damage;
    protected int speed;
}

class Guardian extends Troop {
    public static final Resource TRAIN_COST = new Resource(0, 50);
    public static final int TRAIN_TIME = 10;
    public static final int HITPOINTS = 100;
    public static final TroopTargetType TARGET_TYPE = TroopTargetType.BUILDING;
    public static final int RANGE = 1;
    public static final int DAMAGE = 10;
    public static final int SPEED = 2;

    public Guardian() {
        this.trainCost = TRAIN_COST;
        this.trainTime = TRAIN_TIME;
        this.hitpoints = HITPOINTS;
        this.targetType = TARGET_TYPE;
        this.range = RANGE;
        this.damage = DAMAGE;
        this.speed = SPEED;
    }
}

class Giant extends Troop {
    public static final Resource TRAIN_COST = new Resource(0, 125);
    public static final int TRAIN_TIME = 30;
    public static final int HITPOINTS = 500;
    public static final TroopTargetType TARGET_TYPE = TroopTargetType.RESOURCES;
    public static final int RANGE = 1;
    public static final int DAMAGE = 30;
    public static final int SPEED = 3;

    public Giant() {
        this.trainCost = TRAIN_COST;
        this.trainTime = TRAIN_TIME;
        this.hitpoints = HITPOINTS;
        this.targetType = TARGET_TYPE;
        this.range = RANGE;
        this.damage = DAMAGE;
        this.speed = SPEED;
    }
}

class Dragon extends Troop {
    public static final Resource TRAIN_COST = new Resource(0, 175);
    public static final int TRAIN_TIME = 40;
    public static final int HITPOINTS = 700;
    public static final TroopTargetType TARGET_TYPE = TroopTargetType.BUILDING;
    public static final int RANGE = 3;
    public static final int DAMAGE = 30;
    public static final int SPEED = 6;

    public Dragon() {
        this.trainCost = TRAIN_COST;
        this.trainTime = TRAIN_TIME;
        this.hitpoints = HITPOINTS;
        this.targetType = TARGET_TYPE;
        this.range = RANGE;
        this.damage = DAMAGE;
        this.speed = SPEED;
    }
}

class Archer extends Troop {
    public static final Resource TRAIN_COST = new Resource(0, 60);
    public static final int TRAIN_TIME = 10;
    public static final int HITPOINTS = 100;
    public static final TroopTargetType TARGET_TYPE = TroopTargetType.TOWER;
    public static final int RANGE = 10;
    public static final int DAMAGE = 10;
    public static final int SPEED = 2;

    public Archer() {
        this.trainCost = TRAIN_COST;
        this.trainTime = TRAIN_TIME;
        this.hitpoints = HITPOINTS;
        this.targetType = TARGET_TYPE;
        this.range = RANGE;
        this.damage = DAMAGE;
        this.speed = SPEED;
    }
}

//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}