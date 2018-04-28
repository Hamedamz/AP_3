package models.GameLogic.Entities.Buildings;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Entity;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;

public abstract class DefensiveBuilding extends Building implements Attacker {
    protected int damage;
    protected int range;
    protected BuildingDamageType damageType;
    protected BuildingTargetType targetType;
    protected Destroyable target;

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public BuildingDamageType getDamageType() {
        return damageType;
    }

    public BuildingTargetType getTargetType() {
        return targetType;
    }

    public boolean canTowerAttack(Entity Troop) {

    }

    @Override
    public void giveDamageTo(Destroyable destroyable) {
        destroyable.takeDamageFromAttack(damage);
        if (destroyable.isDestroyed()) {
            destroyable.destroy();
        }
    }

    @Override
    public Destroyable getTarget() {
        return target;
    }


    @Override
    public Destroyable setTarget(BattleGround battleGround) {
        // TODO: 4/19/2018 complete this after battleGround
    }
}
