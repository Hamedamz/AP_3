package models.GameLogic.Entities.Buildings;

import interfaces.Upgradable;
import models.GameLogic.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.enums.MoveType;
import models.ID;
import models.Setting.GameLogicConfig;

public abstract class Building extends Defender implements Upgradable, Comparable<Building> {
    //private int jsonNumber;
    protected int score;
    protected int maxHitPoint;
    protected int hitPoints;
    protected int level;
    private transient ID id; //TODO changed
    protected boolean isDestroyed;
    private boolean isUnderConstruct;

    public Building() {
        super();
    }

    public Building(Position position, ID id) {
        super(position);
        this.id = id;
        String className = this.getClass().getSimpleName();
        this.level = 0;
        this.score = (int) GameLogicConfig.getFromDictionary(className + "DestructionScore");
        this.hitPoints = (int) GameLogicConfig.getFromDictionary(className + "HitPoints");
        this.maxHitPoint = this.hitPoints;
        this.isDestroyed = false;
        if (id.getFirstPartCode().equals("01")) {
            this.isUnderConstruct = true;
        } else {
            this.isUnderConstruct = false;
        }
    }

    public ID getID() {
        return id;
    }

    public boolean isUnderConstruct() {
        return isUnderConstruct;
    }

    public void finishConstruct() {
        isUnderConstruct = false;
    }

    public static MoveType getMoveType() {
        return MoveType.GROUND;
    }

    public static Building getNewBuilding(String buildingType, int x, int y) throws CountLimitReachedException {
        switch (buildingType) {
            case "AirDefense":
                return new AirDefense(new Position(x, y), true);
            case "ArcherTower":
                return new ArcherTower(new Position(x, y), true);
            case "Barracks":
                return new Barracks(new Position(x, y), true);
            case "Camp":
                return new Camp(new Position(x, y), true);
            case "Cannon":
                return new Cannon(new Position(x, y), true);
            case "ElixirMine":
                return new ElixirMine(new Position(x, y), true);
            case "ElixirStorage":
                return new ElixirStorage(new Position(x, y), true);
            case "GoldMine":
                return new GoldMine(new Position(x, y), true);
            case "GoldStorage":
                return new GoldStorage(new Position(x, y), true);
            case "TownHall":
                throw new CountLimitReachedException();
            case "WizardTower":
                return new WizardTower(new Position(x, y), true);
            default:
                break;
        }
        return null;
    }

    public Resource getUpgradeResource() throws UpgradeLimitReachedException {
        return new Resource(GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeGold"),
                GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeElixir"));
    }

    public abstract Bounty getBounty();

    public int getLevel() {
        return level;
    }

    @Override
    public void upgrade() throws UpgradeLimitReachedException {
        level++;
    }

    @Override
    public void takeDamageFromAttack(int damage) {
        hitPoints -= damage;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        if (isDestroyed) {
            return true;
        }
        if (hitPoints <= 0) {
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
        hitPoints += maxHitPoint / getReviveTime();
        if (hitPoints > maxHitPoint) {
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

    @Override
    public int compareTo(Building o) {
        int compare = this.getClass().getSimpleName().compareTo(o.getClass().getSimpleName());
        if (compare == 0) {
            return this.getID().getCount() - o.getID().getCount();
        }
        return compare;
    }
}

//class Wall extends Building {
//}

//class Trap extends DefensiveBuilding {
//}

//class GuardianGiant extends DefensiveBuilding {
//}

