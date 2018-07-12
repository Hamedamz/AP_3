package models.GameLogic.Entities.Buildings;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.GameLogic.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.ID;
import models.setting.GameLogicConfig;
import viewers.BattleGroundScene;

import java.util.ArrayList;

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

    public int getMapRange() {
        return range;
    }

    public BuildingDamageType getDamageType() {
        return damageType;
    }

    public BuildingTargetType getTargetType() {
        return targetType;
    }

    @Override
    public void upgrade() throws UpgradeLimitReachedException {
        super.upgrade();
        damage += GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeDamageAddition");
        hitPoints += GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeHitPointsAddition");
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        if (destroyable == null ) {
            return;
        }
        if (damageType == BuildingDamageType.SINGLE_TARGET) {
            destroyable.takeDamageFromAttack(damage);
            BattleGroundScene.getInstance().attackListener(this, destroyable);
            if (destroyable.isDestroyed()) {
                destroyable.destroy();
            }
        }
        else {
            for (Entity entity : battleGround.getAttackerInPosition(destroyable.getPosition())) {
                if(entity instanceof Destroyable) {
                    ((Destroyable) entity).takeDamageFromAttack(damage);
                    BattleGroundScene.getInstance().attackListener(this, destroyable);
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
    public void findTarget(ArrayList<Destroyable> destroyables) {
        if(target != null) {
            if(target.isDestroyed() || target.getPosition().calculateDistance(getPosition()) < getEffectRange()) {
                target = null;
            }
        }
        if(target == null) {
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
            if (minDistance < this.getEffectRange()) {
                this.target = minDistanceDestroyable;
            }
        }

    }

    @Override
    public int getEffectRange() {
        return getMapRange() * Position.CELL_SIZE;
    }

    @Override
    public void removeTarget() {
        target = null;
    }
}
