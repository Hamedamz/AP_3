package models;

import interfaces.Attacker;
import interfaces.Destroyable;
import models.enums.BuildingDamageType;
import models.enums.BuildingTargetType;

import java.util.ArrayList;

public abstract class Building extends Entity implements Destroyable {
    //private int jsonNumber;
    protected int getMaxHitPoint;
    protected int hitPoints;
    protected int level;
    protected int number;

    Building() {
        //level = 1;
    }

    public abstract Resource getUpgradeResource() ;

    public abstract void upgrade() ;

    public abstract Bounty getBounty();

    public abstract void destroy();

    public int getMaxHitpoint() {
        return getMaxHitPoint;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getLevel() {
        return level;
    }

    public int getNumber() {
        return number;
    }

    public void takeDamageFromAttack(int damage) {

    }

    public boolean isDestroyed() {

    }
}

abstract class DefensiveBuilding extends Building implements Attacker {
    protected int damage;
    protected int range;
    protected BuildingDamageType damageType;
    protected BuildingTargetType targetType;
    protected Destroyable target;

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public BuildingDamageType getDamageType() {
        return damageType;
    }

    public BuildingTargetType getTargetType() {
        return targetType;
    }

    public boolean canTowerAttack(Entity Troop) {

    }

    @Override
    public void giveDamageTo(Destroyable destroyable) {
        destroyable.takeDamageFromAttack(damage);
        if (destroyable.isDestroyed()) {
            destroyable.destroy();
        }
    }

    @Override
    public Destroyable getTarget() {
        return target;
    }

    @Override
    public void destroy() {

    }

    @Override
    public Destroyable setTarget(BattleGround battleGround) {
        // TODO: 4/19/2018 soroushVT complete this after battleGround
    }
}

class ArcherTower extends DefensiveBuilding {
    public ArcherTower(int number) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}

class Cannon extends DefensiveBuilding {

    public Cannon(int number) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}

class AirDefence extends DefensiveBuilding {

    public AirDefence(int number) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}

class WizardTower extends DefensiveBuilding {

    public WizardTower(int number) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}

//class Wall extends Building {
//}

//class Trap extends DefensiveBuilding {
//}

//class GuardianGiant extends DefensiveBuilding {
//}

class Barracks extends Building {

    private ArrayList<TrainingTroop> trainigTroops;
    private int size;

    public Barracks(int number) {

    }

    public boolean hasSpace() {

    }

    public boolean trainNewTroop(TrainingTroop trainingTroop) {

    }

    public boolean removeTroop(String troopName) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }

    @Override
    public void destroy() {

    }
}

class Camp extends Building {

    private ArrayList<Troop> troops;
    private int size;

    public Camp(int number) {

    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addTroop(TrainingTroop trainingTroop) {

    }

    public void removeTroop(String troopName) {

    }

    public boolean hasSpace() {

    }



    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }

    @Override
    public void destroy() {

    }
}

abstract class ResourceBuilding extends Building {
    protected Resource capacity;
    protected Resource stock;

    public Resource getCapacity() {
        return capacity;
    }

    public Resource getStock() {
        return stock;
    }

    public boolean addResources(Resource resource) {

    }

    public boolean removeResources(Resource resource) {

    }

    public boolean hasStock(Resource resource) {

    }

    public boolean isStorageFull() {

    }

    @Override
    public void destroy() {

    }
}

abstract class Mine extends ResourceBuilding{
    private int productionRate;

    public void produce() {

    }
}

abstract class Storage extends ResourceBuilding{

}

class GoldMine extends Mine {

    public GoldMine(int number) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }

}

class ElixirMine extends Mine {

    private int productionRate;

    public ElixirMine(int number) {
    }

    public void produce() {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}

class GoldStorage extends Storage {

    public GoldStorage(int number) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}

class ElixirStorage extends Storage {

    public ElixirStorage(int number) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}

class TownHall extends ResourceBuilding {

    public TownHall(int number) {

    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}