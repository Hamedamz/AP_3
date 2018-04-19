package models;

import interfaces.Destroyable;
import interfaces.MovingAttacker;
import models.enums.TroopTargetType;

public abstract class Troop extends Entity implements Destroyable, MovingAttacker {
    protected int campNumber;
    protected int maxHitpoints;
    protected int hitpoints;
    protected TroopTargetType targetType;
    protected int range;
    protected int damage;
    protected int speed;
    protected Building currentTarget;

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

    @Override
    public void takeDamageFromAttack(int damage) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void giveAttackTo(Destroyable destroyable) {

    }

    @Override
    public Destroyable setTarget(BattleGround battleGround) {
        return null;
    }

    @Override
    public Destroyable getTarget() {
        return null;
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