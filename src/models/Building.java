package models;

import models.enums.BuildingDamageType;
import models.enums.BuildingTargetType;

import java.util.ArrayList;

public abstract class Building {
    //private int jsonNumber;
    protected Resource buildCost;
    protected int buildTime;
    protected int hitpoints;
    protected Bounty bounty;
    protected int level;
}

abstract class DefensiveBuilding extends Building {
    protected int damage;
    protected int range;
    protected BuildingDamageType damageType;
    protected BuildingTargetType targetType;
}

class ArcherTower extends DefensiveBuilding {
    public static final Resource BUILD_COST = new Resource(300, 0);
    public static final int BUILD_TIME = 60;
    public static final int HITPOINTS = 300;
    public static final int DAMAGE = 20;
    public static final int RANGE = 10;
    public static final Bounty BOUNTY = new Bounty(3, BUILD_COST);
    public static final BuildingDamageType DAMAGE_TYPE = BuildingDamageType.SINGLE_TARGET;
    public static final BuildingTargetType TARGET_TYPE = BuildingTargetType.GROUND;

    public ArcherTower() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
        this.damage = DAMAGE;
        this.range = RANGE;
        this.damageType = DAMAGE_TYPE;
        this.targetType = TARGET_TYPE;
    }


}

class Cannon extends DefensiveBuilding {
    public static final Resource BUILD_COST = new Resource(400, 0);
    public static final int BUILD_TIME = 100;
    public static final int HITPOINTS = 400;
    public static final int DAMAGE = 20;
    public static final int RANGE = 13;
    public static final Bounty BOUNTY = new Bounty(4, BUILD_COST);
    public static final BuildingDamageType DAMAGE_TYPE = BuildingDamageType.AREA_SPLASH;
    public static final BuildingTargetType TARGET_TYPE = BuildingTargetType.GROUND;

    public Cannon() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
        this.damage = DAMAGE;
        this.range = RANGE;
        this.damageType = DAMAGE_TYPE;
        this.targetType = TARGET_TYPE;
    }
}

class AirDefence extends DefensiveBuilding {
    public static final Resource BUILD_COST = new Resource(300, 0);
    public static final int BUILD_TIME = 60;
    public static final int HITPOINTS = 300;
    public static final int DAMAGE = 20;
    public static final int RANGE = 10;
    public static final Bounty BOUNTY = new Bounty(3, BUILD_COST);
    public static final BuildingDamageType DAMAGE_TYPE = BuildingDamageType.SINGLE_TARGET;
    public static final BuildingTargetType TARGET_TYPE = BuildingTargetType.AIR;

    public AirDefence() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
        this.damage = DAMAGE;
        this.range = RANGE;
        this.damageType = DAMAGE_TYPE;
        this.targetType = TARGET_TYPE;
    }
}

class WizardTower extends DefensiveBuilding {
    public static final Resource BUILD_COST = new Resource(500, 0);
    public static final int BUILD_TIME = 120;
    public static final int HITPOINTS = 700;
    public static final int DAMAGE = 20;
    public static final int RANGE = 13;
    public static final Bounty BOUNTY = new Bounty(5, BUILD_COST);
    public static final BuildingDamageType DAMAGE_TYPE = BuildingDamageType.AREA_SPLASH;
    public static final BuildingTargetType TARGET_TYPE = BuildingTargetType.GROUND_AND_AIR;

    public WizardTower() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
        this.damage = DAMAGE;
        this.range = RANGE;
        this.damageType = DAMAGE_TYPE;
        this.targetType = TARGET_TYPE;
    }
}

//class Wall extends Building {
//}

//class Trap extends DefensiveBuilding {
//}

//class GuardianGiant extends DefensiveBuilding {
//}

class Barracks extends Building {
    public static final Resource BUILD_COST = new Resource(200, 0);
    public static final int BUILD_TIME = 100;
    public static final int HITPOINTS = 300;
    public static final Bounty BOUNTY = new Bounty(1, BUILD_COST);

    public Barracks() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
    }
}

class Camp extends Building {
    public static final Resource BUILD_COST = new Resource(200, 0);
    public static final int BUILD_TIME = 100;
    public static final int HITPOINTS = 900;
    public static final Bounty BOUNTY = new Bounty(1, BUILD_COST);
    public static final int CAPACITY = 50;

    private ArrayList<Troop> troops;

    public Camp() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
    }
}

abstract class ResourceBuilding extends Building {
}

class GoldMine extends ResourceBuilding {
    public static final Resource BUILD_COST = new Resource(150, 5);
    public static final int BUILD_TIME = 0;
    public static final int HITPOINTS = 200;
    public static final Bounty BOUNTY = new Bounty(2, BUILD_COST);

    private int productionRate;

    public GoldMine() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
    }
}

class ElixirMine extends ResourceBuilding {
    public static final Resource BUILD_COST = new Resource(100, 3);
    public static final int BUILD_TIME = 100;
    public static final int HITPOINTS = 200;
    public static final Bounty BOUNTY = new Bounty(2, BUILD_COST);

    private int productionRate;

    public ElixirMine() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
    }
}

class GoldStorage extends ResourceBuilding {
    public static final Resource BUILD_COST = new Resource(200, 0);
    public static final int BUILD_TIME = 0;
    public static final int HITPOINTS = 300;
    public static final Bounty BOUNTY = new Bounty(3, BUILD_COST); // FIXME plus storage gold

    private int capacity;

    public GoldStorage() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
    }
}

class ElixirStorage extends ResourceBuilding {
    public static final Resource BUILD_COST = new Resource(200, 0);
    public static final int BUILD_TIME = 0; //FIXME
    public static final int HITPOINTS = 300;
    public static final Bounty BOUNTY = new Bounty(3, BUILD_COST); // FIXME plus storage elixir

    private int capacity;

    public ElixirStorage() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
    }
}

class TownHall extends ResourceBuilding {
    public static final Resource BUILD_COST = new Resource(200, 0);
    public static final int BUILD_TIME = 100;
    public static final int HITPOINTS = 1000;
    public static final Bounty BOUNTY = new Bounty(8, BUILD_COST);

    private Resource capacity;

    public TownHall() {
        this.buildCost = BUILD_COST;
        this.buildTime = BUILD_TIME;
        this.hitpoints = HITPOINTS;
        this.bounty = BOUNTY;
        //this.level = ;
    }
}