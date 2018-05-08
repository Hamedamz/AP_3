package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import interfaces.Movable;
import interfaces.Upgradable;
import interfaces.Vulnerable;
import models.GameLogic.BFS;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.MoveType;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;

public abstract class Troop extends Entity implements Movable, Upgradable, Vulnerable {
    protected MoveType moveType;
    protected int speed;
    private int level;

    private ArrayList<Position> movementPath;

    public Troop() {
        super();
        level = 0;
        String className = this.getClass().getSimpleName();
        this.speed = (int) GameLogicConfig.getFromDictionary(className + "Speed");
    }

    @Override
    public void move() {
        this.position = this.movementPath.get(Math.min(speed, getPath().size() - 1));
    }

    public void setMovementPath(ArrayList<Position> movementPath) {
        this.movementPath = movementPath;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public MoveType getTroopMoveType() {
        return moveType;
    }

    @Override
    public Resource getUpgradeResource() {
        return new Resource(0, 0);
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public ArrayList<Position> getPath() {
        return movementPath;
    }

    public static Troop castStringToTroopType(String string) {
        switch (string) {
            case "Giant" :
                return new Giant();
            case "Archer" :
                return new Archer();
            case "Dragon":
                return new Dragon();
            case "Guardian" :
                return new Guardian();

        }
        return null;
    }
}


//class WallBreaker extends Troop {
//
//}

//class Healer extends Troop {
//
//}