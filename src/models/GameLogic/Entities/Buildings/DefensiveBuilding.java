package models.GameLogic.Entities.Buildings;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.GameLogic.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.ID;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class DefensiveBuilding extends Building implements Attacker {
    protected int damage;
    protected int range;
    protected BuildingDamageType damageType;
    protected BuildingTargetType targetType;
    protected Destroyable target;

    public DefensiveBuilding(Position position, ID id) {
        super(position, id);
        String className = this.getClass().getSimpleName();
        this.damage = (int) GameLogicConfig.getFromDictionary(className + "Damage");
        this.range = (int) GameLogicConfig.getFromDictionary(className + "Range");
    }

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
        return true;
        // TODO: 5/5/2018
    }

    @Override
    public void upgrade() throws UpgradeLimitReachedException {
        super.upgrade();
        damage += GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeDamageAddition");
        hitPoints += GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeHitPointsAddition");
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        if (damageType == BuildingDamageType.SINGLE_TARGET) {
            destroyable.takeDamageFromAttack(damage);
            if (destroyable.isDestroyed()) {
                destroyable.destroy();
            }
        }
        else {
            for (Entity entity : battleGround.getAttackerEntitiesInPosition(destroyable.getPosition())) {
                if(entity instanceof Destroyable) {
                    ((Destroyable) entity).takeDamageFromAttack(damage);
                    if (destroyable.isDestroyed()) {
                        destroyable.destroy();
                    }
                }
            }
        }
    }



    @Override
    public Destroyable getTarget() {
        return target;
    }

    @Override
    public void setTarget(ArrayList<Destroyable> destroyables) {
        double minDistance = Double.MAX_VALUE;
        Destroyable minDistanceDestroyable = null;
        for (Destroyable destroyable : destroyables) {
            if (!destroyable.isDestroyed() && BuildingTargetType.isBuildingTargetAppropriate(this, (AttackerTroop) destroyable)) {
                double distance = this.getPosition().calculateDistance(destroyable.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistanceDestroyable = destroyable;
                }
            }
        }
        if (minDistance < Double.MAX_VALUE && minDistance < this.getRange()) {
            this.target = minDistanceDestroyable;
        }
        this.target = null;
    }

    @Override
    public int getEffectRange() {
        return getRange();
    }
}
