package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import interfaces.Movable;
import interfaces.TimedEvent;
import models.GameLogic.BattleGround;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.GameLogic.utills.PathFinder;
import models.setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.Arrays;

public class Healer extends Troop implements TimedEvent {
    private int healingRange;
    private int healingAmount;
    private int remainingTime;
    private int maxTime;

    private Destroyable target;

    public Healer() {
        moveType = MoveType.AIR;
        healingAmount = GameLogicConfig.getFromDictionary("HealerHealingAmount");
        remainingTime = GameLogicConfig.getFromDictionary("HealerDeathTime");
        maxTime = remainingTime;
        healingRange = GameLogicConfig.getFromDictionary("HealerRange");
    }

    @Override
    public int getEffectRange() {
        return healingRange * Position.CELL_SIZE;
    }

    @Override
    public void update(BattleGround battleGround, int turnPerSecond, int turn) {
        boolean isBigTurn = (turn % turnPerSecond == 0);
        if (getTarget() == null || getTarget().isDestroyed() || isBigTurn) {
            findTarget(battleGround);
        }
        if (getPath() == null || isBigTurn) {
            findPath(battleGround);
        }
        if (((turn + 1) * getSpeed() / turnPerSecond) > (turn * getSpeed() / turnPerSecond)) {
            move();
        }
        if (isBigTurn) {
            heal(battleGround);
            reduceTime();
        }
    }

    @Override
    public void findTarget(BattleGround battleGround) {
        if (target != null) {
            if (target.getHitPoints() >= target.getMaxHitPoints() || target.isDestroyed()) {
                target = null;
            }
        }
        if (target == null) {
            double minDistance = Double.MAX_VALUE;
            Destroyable nearestAlly = null;
            for (Troop troop : battleGround.getDeployedTroops()) {
                if (troop instanceof Destroyable) {
                    Destroyable destroyable = (Destroyable) troop;
                    if (!destroyable.isDestroyed() && destroyable.getHitPoints() < destroyable.getMaxHitPoints()) {
                        double dis = destroyable.getPosition().calculateDistance(getPosition());
                        if (dis < minDistance) {
                            minDistance = dis;
                            nearestAlly = destroyable;
                        }
                    }
                }
            }
            target = nearestAlly;
        }
    }

    @Override
    public void findPath(BattleGround battleGround) {
        movementCounter = 0;
        if (target != null) {
            setMovementPath(PathFinder.getPath(battleGround.getEnemyGameMap(), this, getTarget().getPosition(),
                    Math.max(this.getEffectRange() - ((Movable) getTarget()).getSpeed(), 0)));
        } else {
            setMovementPath(new ArrayList<>(Arrays.asList(getPosition())));
        }
    }

    @Override
    public Destroyable getTarget() {
        return target;
    }

    @Override
    public boolean isDestroyed() {
        return remainingTime <= 0;
    }

    public void heal(BattleGround battleGround) {
        for (Troop troop : battleGround.getDeployedTroops()) {
            if (troop.getPosition().calculateDistance(getPosition()) <= getEffectRange() && troop instanceof AttackerTroop) {
                if (!troop.isDestroyed()) {
                    ((AttackerTroop) troop).heal(healingAmount);
                }
            }
        }
    }

    @Override
    public void removeTarget() {
        target = null;
    }

    @Override
    public void reduceTime() {
        remainingTime--;
    }

    @Override
    public void upgrade() {
        setLevel(getLevel() + 1);
        healingAmount += GameLogicConfig.getFromDictionary("HealerUpgradeHealingAmountAddition");
    }

    public int getHealingRange() {
        return healingRange;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getMaxTime() {
        return maxTime;
    }
}
