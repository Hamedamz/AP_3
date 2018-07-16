package models.GameLogic.Entities.Buildings;

import models.interfaces.Attacker;
import models.interfaces.Destroyable;
import models.GameLogic.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.ID;
import models.setting.GameLogicConfig;
import models.setting.GameLogicConstants;
import viewers.BattleGroundScene;

public abstract class DefensiveBuilding extends Building implements Attacker {
    protected int attackSpeed;
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
        this.attackSpeed = GameLogicConfig.getFromDictionary(className + "AttackSpeed");
        this.attackCounter = Integer.MAX_VALUE/2;
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
    }

    protected int attackCounter;

    @Override
    public void update(BattleGround battleGround, int turnPerSecond, int turn) {
        boolean isBigTurn = (turn % turnPerSecond == 0);
        attackCounter++;
        if (getTarget() == null || getTarget().isDestroyed() || isBigTurn) {
            try {
                findTarget(battleGround);
            } catch (NoTargetFoundException e) {
                return;
            }
        }
        if (!isDestroyed()) {
            if(attackCounter >= GameLogicConstants.DEFAULT_ATTACK_SPEED * turnPerSecond / getAttackSpeed()) {
                giveDamageTo(getTarget(), battleGround);
            }
        }
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        if (destroyable == null) {
            return;
        }
        if (damageType == BuildingDamageType.SINGLE_TARGET) {
            attackCounter = 0;
            destroyable.takeDamageFromAttack(damage);
            BattleGroundScene.getInstance().attackHappened(this, destroyable);
            if (destroyable.isDestroyed()) {
                destroyable.destroy();
            }
        }
        else {
            for (Entity entity : battleGround.getAttackerInPosition(destroyable.getPosition())) {
                if(entity instanceof Destroyable) {
                    attackCounter = 0;
                    ((Destroyable) entity).takeDamageFromAttack(damage);
                    BattleGroundScene.getInstance().attackHappened(this, destroyable);
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
    public void findTarget(BattleGround battleGround) throws NoTargetFoundException {
        if(target != null) {
            if(target.isDestroyed() || target.getPosition().calculateDistanceFromBuilding(getPosition(), getSize()) > getEffectRange()) {
                target = null;
            }
        }
        if(target == null) {
            double minDistance = Double.MAX_VALUE;
            Destroyable minDistanceDestroyable = null;
            for (Troop troop : battleGround.getDeployedTroops()) {
                if(troop instanceof Destroyable) {
                    Destroyable destroyable = (Destroyable) troop;
                    if (!destroyable.isDestroyed() && BuildingTargetType.isBuildingTargetAppropriate(this, (AttackerTroop) destroyable)) {
                        double distance = destroyable.getPosition().calculateDistanceFromBuilding(this.getPosition(), getSize());
                        if (distance < minDistance) {
                            minDistance = distance;
                            minDistanceDestroyable = destroyable;
                        }
                    }

                }
            }
            if (minDistance < this.getEffectRange()) {
                this.target = minDistanceDestroyable;
            }
        }
        throw new NoTargetFoundException();
    }

    @Override
    public int getEffectRange() {
        return getMapRange() * Position.CELL_SIZE;
    }

    @Override
    public int getAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public void removeTarget() {
        target = null;
    }
}
