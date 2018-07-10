package models.GameLogic.Entities.Buildings;

import interfaces.Upgradable;
import javafx.geometry.Rectangle2D;
import models.GameLogic.*;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.enums.MoveType;
import models.GameLogic.ID;
import models.Setting.GameLogicConfig;
import viewers.utils.Const;

public abstract class Building extends Defender implements Upgradable, Comparable<Building> {
    protected int score;
    protected int maxHitPoint;
    protected int hitPoints;
    protected int level;
    private  ID id;
    protected boolean isDestroyed;
    private boolean isUnderConstruct;
    private static int maxLevel = 0;
    protected int size;

    public Building() {
        super();
    } // FIXME: 6/24/2018 

    public Building(Position position, ID id) {
        super(position);
        this.id = id;
        String className = this.getClass().getSimpleName();
        this.level = 0;
        this.size = GameLogicConfig.getFromDictionary(className + "Size");
        this.score = (int) GameLogicConfig.getFromDictionary(className + "DestructionScore");
        this.hitPoints = (int) GameLogicConfig.getFromDictionary(className + "HitPoints");
        this.maxHitPoint = this.hitPoints;
        this.isDestroyed = false;
        if (id.getFirstPartCode().equals("01")) {
            this.isUnderConstruct = true;
        } else {
            this.isUnderConstruct = false;
        }
        updateViewPort();

    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public static void setMaxLevel(int maxLevelToSet) {
        maxLevel = maxLevelToSet;
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
                return new AirDefense(Position.newMapPosition(x, y), true);
            case "ArcherTower":
                return new ArcherTower(Position.newMapPosition(x, y), true);
            case "Barracks":
                return new Barracks(Position.newMapPosition(x, y), true);
            case "Camp":
                return new Camp(Position.newMapPosition(x, y), true);
            case "Cannon":
                return new Cannon(Position.newMapPosition(x, y), true);
            case "ElixirMine":
                return new ElixirMine(Position.newMapPosition(x, y), true);
            case "ElixirStorage":
                return new ElixirStorage(Position.newMapPosition(x, y), true);
            case "GoldMine":
                return new GoldMine(Position.newMapPosition(x, y), true);
            case "GoldStorage":
                return new GoldStorage(Position.newMapPosition(x, y), true);
            case "GuardianGiant":
                return new GuardianGiant(Position.newMapPosition(x, y), true);
            case "TownHall":
                throw new CountLimitReachedException();
            case "Trap":
                return new Trap(Position.newMapPosition(x, y), true);
            case "Wall":
                return new Wall(Position.newMapPosition(x, y), true);
            case "WizardTower":
                return new WizardTower(Position.newMapPosition(x, y), true);
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
        if (level >= maxLevel) {
            throw new UpgradeLimitReachedException();
        }
        if (isUnderConstruct) {
            throw new UpgradeLimitReachedException();
        }
        level++;
        updateViewPort();
    }

    public void updateViewPort() {
        double offset = level * Const.ENTITY_TILE_WIDTH;
        double width = Const.ENTITY_TILE_WIDTH;
        double height = Const.ENTITY_TILE_HEIGHT;
        if (this.getClass().equals(TownHall.class)) {
            offset = level * Const.TOWNHALL_TILE_WIDTH;
            width = Const.TOWNHALL_TILE_WIDTH;
            height = Const.TOWNHALL_TILE_HEIGHT;
        }
        if (offset >= getImageView().getImage().getWidth()) {
            return;
        }
        getImageView().setViewport(new Rectangle2D(offset, 0, width, height));
    }

    @Override
    public void takeDamageFromAttack(int damage) {
        hitPoints -= damage;
    }

    @Override
    public void destroy() {
        isDestroyed = true;
        this.hitPoints = 0;
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

    public int getSize() {
        return size;
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

