package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import interfaces.MovingAttacker;
import models.GameLogic.BFS;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.GameLogic.enums.TroopTargetType;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;

public abstract class AttackerTroop extends Troop implements MovingAttacker, Destroyable {
    protected TroopTargetType targetType;
    protected int maxHitPoints;
    protected int hitPoints;
    protected int range;
    protected int damage;
    protected Defender currentTarget;

    public AttackerTroop() {
        String className = this.getClass().getSimpleName();
        this.damage = (int) GameLogicConfig.getFromDictionary(className + "Damage");
        this.range = (int) GameLogicConfig.getFromDictionary(className + "Range");
        this.hitPoints = (int) GameLogicConfig.getFromDictionary(className + "HitPoints");
        this.maxHitPoints = this.hitPoints;
    }

    public TroopTargetType getTargetType() {
        return targetType;
    }

    @Override
    public Destroyable getTarget() {
        return currentTarget;
    }

    @Override
    public void setTarget(ArrayList<Destroyable> destroyables) throws NoTargetFoundException {
        double minDistance = Double.MAX_VALUE;
        Destroyable minDistantDestroyable = null;
        for (Destroyable destroyable : destroyables) {
            if (!destroyable.isDestroyed() && TroopTargetType.isTroopTargetAppropriate(this, (Defender) destroyable)) {
                double distance = this.getPosition().calculateDistance(destroyable.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistantDestroyable = destroyable;
                }
            }
        }
        if (minDistance < Double.MAX_VALUE) {
            currentTarget = (Defender) minDistantDestroyable;
            return;
        }

        for (Destroyable destroyable : destroyables) {
            if (!destroyable.isDestroyed()) {
                double distance = this.getPosition().calculateDistance(destroyable.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistantDestroyable = destroyable;
                }
            }
        }

        if(minDistance < Double.MAX_VALUE) {
            currentTarget = (Defender) minDistantDestroyable;
            return;
        }
        throw new NoTargetFoundException();
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        destroyable.takeDamageFromAttack(damage);
        if (destroyable.isDestroyed()) {
            destroyable.destroy();
        }
    }

    @Override
    public void findPath(BattleGround battleGround) {
        setMovementPath(BFS.getPath(battleGround.getEnemyMap(), this.position, getTarget().getPosition(), this.range));
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
    public void upgrade() {
        setLevel(getLevel() + 1);
        damage += GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeDamageAddition");
        hitPoints += GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeHitPointsAddition");
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


    @Override
    public MoveType getTroopType() {
        return null;
        // TODO: 5/5/2018  
    }

    @Override
    public int getEffectRange() {
        return range;
    }
}

