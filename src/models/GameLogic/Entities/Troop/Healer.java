package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import interfaces.TimedEvent;
import models.GameLogic.BattleGround;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Position;
import models.GameLogic.enums.MoveType;
import models.setting.GameLogicConfig;
import viewers.GameScene;

import java.util.ArrayList;

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
    public void setTarget(ArrayList<Destroyable> tList) throws NoTargetFoundException {
        if (target != null) {
            if(target.getHitPoints() >= target.getHitPoints() || target.isDestroyed()) {
                target = null;
            }
        }
        if (target == null){
            double minDistance = Double.MAX_VALUE;
            Destroyable nearestAlly = null;
            for(Destroyable destroyable : tList) {
                if(!destroyable.isDestroyed() && destroyable.getHitPoints() < destroyable.getMaxHitPoints()) {
                    double dis = destroyable.getPosition().calculateDistance(getPosition());
                    if(dis < minDistance) {
                        minDistance = dis;
                        nearestAlly = destroyable;
                    }
                }
            }
            if(nearestAlly == null) {
                throw new NoTargetFoundException();
            }
            target = nearestAlly;
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
        for(Troop troop : battleGround.getDeployedTroops()) {
            if(troop.getPosition().calculateDistance(getPosition()) <= getEffectRange() && troop instanceof AttackerTroop) {
                if(!troop.isDestroyed()){
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
