package models.GameLogic.Entities.Troop;

import models.interfaces.Movable;
import models.interfaces.Updatable;
import models.interfaces.Upgradable;
import models.interfaces.Vulnerable;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.MoveType;
import models.GameLogic.utills.PathFinder;
import models.setting.GameLogicConfig;
import viewers.AppGUI;

import java.util.ArrayList;

public abstract class Troop extends Entity implements Movable, Updatable, Upgradable, Vulnerable {
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

    public Position getNextPosition() {
        return this.movementPath.get(Math.min(movementCounter, getPath().size() - 1));
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

    protected int movementCounter = 0;

    @Override
    public void findPath(BattleGround battleGround) {
        movementCounter = 0;
        setMovementPath(PathFinder.getPath(battleGround.getEnemyGameMap(), this, getTarget().getPosition(), this.getEffectRange()));
    }

    @Override
    public void move() {
        movementCounter++;
        int dir = calculateDirection();
        this.position = getNextPosition();
        AppGUI.getBattleGroundScene().movementHappened(dir, this);
    }

    private int calculateDirection() {
        if(getPosition().equals(getNextPosition())) {
            return 0;
        }
        if(getPosition().getX() < getNextPosition().getX() || getPosition().getY() > getNextPosition().getY()) {
            return -1;
        }
        return 1;
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
        return speed * Position.CELL_SIZE;
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
            case "WallBreaker":
                return new WallBreaker();
            case "Healer":
                return new Healer();

        }
        return null;
    }
}