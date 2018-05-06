package models.GameLogic.Entities.Buildings;

import interfaces.Upgradable;
import models.GameLogic.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Bounty;
import models.GameLogic.enums.MoveType;
import models.ID;
import models.Setting.GameLogicConfig;

import java.util.Comparator;

public abstract class Building extends Defender implements Upgradable {
    //private int jsonNumber;
    protected int score;
    protected int maxHitPoint;
    protected int hitPoints;
    protected int level;
    private ID id;
    protected boolean isDestroyed;
    protected boolean isUnderConstruct;

    public Building(Position position, ID id) {
        super(position);
        this.id = id;
        String className = this.getClass().getSimpleName();
        this.level = 0;
        this.score = (int) GameLogicConfig.getFromDictionary(className + "DestructionScore");
        this.hitPoints = (int) GameLogicConfig.getFromDictionary(className + "HitPoints");
        this.maxHitPoint = this.hitPoints;
        this.isDestroyed = false;
        this.isUnderConstruct = false;
    }

    public ID getID() {
        return id;
    }

    public static MoveType getMoveType() {
        return MoveType.Ground;
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
        return 0;
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

