package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import models.GameLogic.BattleGround;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import sun.security.krb5.internal.crypto.Des;

import java.util.ArrayList;

public class Healer extends Troop {
    private int healingRange;
    private int healingAmount;
    private int remiainigTime;

    private Destroyable target;

    public Healer() {
        // TODO: 6/24/2018
    }

    @Override
    public int getEffectRange() {
        return healingRange;
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
    public void upgrade() throws UpgradeLimitReachedException {
        // TODO: 6/24/2018 arshia moghimi
    }

    @Override
    public boolean isDestroyed() {
        return remiainigTime <= 0;
    }

    public void heal(BattleGround battleGround) {
        for(Troop troop : battleGround.getDeployedTroops()) {
            if(troop.getPosition().calculateDistance(getPosition()) <= healingRange && troop instanceof AttackerTroop) {
                if(!troop.isDestroyed()){
                    ((AttackerTroop) troop).heal(healingAmount);
                }
            }
        }
    }

    public void reduceRemainingTime(){
        remiainigTime--;
    }
}
