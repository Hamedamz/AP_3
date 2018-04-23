package models;

import interfaces.Destroyable;
import interfaces.MovingAttacker;
import models.enums.TroopTargetType;

abstract class AttackerTroop extends Troop implements MovingAttacker, Destroyable {
    protected TroopTargetType targetType;
    protected int maxHitPoints;
    protected int hitPoints;
    protected int range;
    protected int damage;
    protected Building currentTarget;

    @Override
    public Destroyable getTarget() {
        return currentTarget;
    }

    @Override
    public Destroyable setTarget(BattleGround battleGround) {
        // TODO: 4/23/2018 fixme after BattleGround
    }

    @Override
    public void giveDamageTo(Destroyable destroyable) {
        destroyable.takeDamageFromAttack(damage);
        if (destroyable.isDestroyed()) {
            destroyable.destroy();
        }
    }

    @Override
    public void takeDamageFromAttack(int damage) {
        hitPoints -= damage;
    }

    @Override
    public void destroy() {
        //really?
    }

    @Override
    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public int getMaxHitPoints() {
        return maxHitPoints;
    }
}

class Guardian extends AttackerTroop {

    public Guardian(Dictionary dictionary) {
    }
}

class Giant extends AttackerTroop {

    public Giant(Dictionary dictionary) {

    }
}

class Dragon extends AttackerTroop {

    public Dragon(Dictionary dictionary) {

    }
}

class Archer extends AttackerTroop {

    public Archer(Dictionary dictionary) {

    }

}