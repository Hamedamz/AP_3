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
    private int damage;
    protected Defender currentTarget;

    public AttackerTroop() {
        String className = this.getClass().getSimpleName();
        this.damage =  GameLogicConfig.getFromDictionary(className + "Damage");
        this.range =  GameLogicConfig.getFromDictionary(className + "Range");
        this.hitPoints = GameLogicConfig.getFromDictionary(className + "HitPoints");
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
    public int getEffectRange() {
        return range;
    }
}

