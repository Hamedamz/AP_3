package models;

import models.enums.TroopTargetType;

public class Troop extends Entity{
    //protected Resource trainCost;
    //protected int trainTime;
    protected int maxHitpoints;
    protected int hitpoints;
    protected TroopTargetType targetType;
    protected int range;
    protected int damage;
    protected int speed;

    public int getMaxHitpoints() {
        return maxHitpoints;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public TroopTargetType getTargetType() {
        return targetType;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void takeDamageFromAttack() {
    }

    public boolean isAlive() {

    }
}

class Guardian extends Troop {

    public Guardian(Dictionary dictionary) {
    }
}

class Giant extends Troop {

    public Giant(Dictionary dictionary) {

    }
}

class Dragon extends Troop {

    public Dragon(Dictionary dictionary) {

    }
}

class Archer extends Troop {

    public Archer(Dictionary dictionary) {

    }

}

//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}