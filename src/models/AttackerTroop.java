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
        //todo soroushVT fixme
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
        // TODO: 4/23/2018  soroushVT
    }

    @Override
    public void destroy() {
        // TODO: 4/23/2018 soroushVT
    }

    @Override
    public boolean isDestroyed() {
        // TODO: 4/23/2018 soroushVT
    }

    @Override
    public int getHitPoints() {
        // TODO: 4/23/2018 soroushVT
    }

    @Override
    public int getMaxHitPoints() {
        // TODO: 4/23/2018 soroushVT
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