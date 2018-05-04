package models.GameLogic.Entities.Buildings;

import interfaces.Attacker;
import interfaces.Destroyable;
import interfaces.Revivable;
import interfaces.Upgradable;
import models.GameLogic.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NotEnoughResourcesException;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.Bounty;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Building extends Entity implements Revivable, Upgradable {
    //private int jsonNumber;
    protected int score;
    protected int maxHitPoint;
    protected int hitPoints;
    protected int level;
    protected int number;
    protected boolean isDestroyed;
    protected boolean isUnderConstruct;

    public Building(Position position, int number) {
        super(position);
        this.number = number;
        String className = this.getClass().getName();
        this.level = 0;
        this.score = (Integer) GameLogicConfig.getFromDictionary(className + "DestructionScore");
        this.hitPoints = (Integer) GameLogicConfig.getFromDictionary(className + "HitPoints");
        this.maxHitPoint = this.hitPoints;
        this.isDestroyed = false;
        this.isUnderConstruct = false;
        this.number = 0;  //FIXME IDGenerator
    }

    public boolean isUnderConstruct() {
        return isUnderConstruct;
    }

    public void finishConstruct() {
        isUnderConstruct = false;
    }

    public abstract Resource getUpgradeResource() ;

    public abstract void upgrade();

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

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static class BuildingComparator implements Comparator<Building> {

        @Override
        public int compare(Building o1, Building o2) {
            return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
        }
    }
}

//class Wall extends Building {
//}

//class Trap extends DefensiveBuilding {
//}

//class GuardianGiant extends DefensiveBuilding {
//}

