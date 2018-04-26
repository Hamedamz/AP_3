package models.GameLogic;

import interfaces.Attacker;
import interfaces.Destroyable;
import interfaces.Revivable;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;

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
    public Destroyable setTarget(BattleGround battleGround) {
        // TODO: 4/19/2018 complete this after battleGround
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

    public ArrayList<TrainingTroop> getTrainingTroops() {
        return trainigTroops;
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
    public void performLosses() {
        troops = new ArrayList<>();
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
    public void takeDamageFromAttack(int damage) {
        super.takeDamageFromAttack(damage);
    }

    @Override
    public void destroy() {
        stock.setElixir(0);
        stock.setGold(0);
        super.destroy();
    }


}

abstract class Mine extends Building{
    protected int productionRate;

    public abstract Resource produce();
}

abstract class Storage extends ResourceBuilding{

}

class GoldMine extends Mine {

    public GoldMine(int number) {

    }

    @Override
    public Resource produce() {
        return new Resource(productionRate, 0);
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

    @Override
    public Resource produce() {
        return new Resource(0, productionRate);
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

    private ArrayList<Builder> builders;

    public TownHall() {

    }

    public ArrayList<Builder> getBuilders() {
        return builders;
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

