package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import interfaces.MovingAttacker;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Map;
import models.GameLogic.Position;
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
        String className = this.getClass().getName();
        this.range = (Integer) GameLogicConfig.getFromDictionary(className + "Range");
        this.hitPoints = (Integer) GameLogicConfig.getFromDictionary(className + "HitPoints");
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
        double minDistance = -1;
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
        if (minDistance >= 0) {
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

        if(minDistance >= 0) {
            currentTarget = (Defender) minDistantDestroyable;
            return;
        }
        throw new NoTargetFoundException();
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, Map map) {
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

