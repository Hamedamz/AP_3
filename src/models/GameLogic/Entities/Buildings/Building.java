package models.GameLogic.Entities.Buildings;

import interfaces.Attacker;
import interfaces.Destroyable;
import interfaces.Revivable;
import models.GameLogic.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.Bounty;

import java.util.ArrayList;

public abstract class Building extends Entity implements Revivable {
    //private int jsonNumber;
    protected int maxHitPoint;
    protected int hitPoints;
    protected int level;
    protected int number;
    protected boolean isDestroyed = false; //fixme put this false in constructor
    protected boolean isUnderConstruct = true; //in constructor

    Building() {
        //level = 1;
    }

    public boolean isUnderConstruct() {
        return isUnderConstruct;
    }

    public void finishConstruct() {
        isUnderConstruct = false;
    }

    public abstract Resource getUpgradeResource() ;

    public abstract void upgrade() ;

    public abstract Bounty getBounty();

    public int getLevel() {
        return level;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void takeDamageFromAttack(int damage) {
        hitPoints -= damage;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
        return;
    }

    @Override
    public boolean isDestroyed() {
        if(isDestroyed) {
            return true;
        }
        if(hitPoints <= 0) {
            isDestroyed = true;
        }
        return isDestroyed;
    }

    @Override
    public int getMaxHitPoints() {
        return maxHitPoint;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public void revive() {
        hitPoints += maxHitPoint/getReviveTime();
        if(hitPoints > maxHitPoint) {
            isDestroyed = false;
            hitPoints = maxHitPoint;
        }
    }

    @Override
    public int getReviveTime() {
        // TODO: 4/23/2018 fixme after dictionary implementation
    }

    @Override
    public void performLosses() {
        //really?
    }
}

//class Wall extends Building {
//}

//class Trap extends DefensiveBuilding {
//}

//class GuardianGiant extends DefensiveBuilding {
//}

