package models.GameLogic.Entities.Buildings;

import interfaces.Destroyable;
import interfaces.MovingAttacker;
import models.GameLogic.BattleGround;
import models.GameLogic.Bounty;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.enums.MoveType;
import models.GameLogic.utills.PathFinder;
import models.GameLogic.utills.IDGenerator;
import models.setting.GameLogicConfig;

import java.util.ArrayList;

public class GuardianGiant extends DefensiveBuilding implements MovingAttacker {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 14);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 14);

    public GuardianGiant(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        initialPosition = position;
        speed = GameLogicConfig.getFromDictionary("GuardianGiantSpeed");
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.SINGLE_TARGET;
    }

    private Position initialPosition;

    private int speed;
    private ArrayList<Position> movementPath;

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("GuardianGiantBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }

    private int movementCounter = 0;

    @Override
    public MoveType getTroopMoveType() {
        return MoveType.GROUND;
    }

    @Override
    public void findPath(BattleGround battleGround) {
        movementCounter = 0;
        PathFinder.getPath(battleGround.getEnemyGameMap(), this, target.getPosition(), getEffectRange());
    }

    @Override
    public void move() {
        movementCounter++;
        setPosition(getPath().get(Math.min(getPath().size() - 1, movementCounter)));
    }

    @Override
    public ArrayList<Position> getPath() {
        return null;
    }

    @Override
    public int getSpeed() {
        return speed * Position.CELL_SIZE;
    }

    public int getMovementCounter() {
        return movementCounter;
    }

    public void resetPosition() {
        position = initialPosition;
    }

    @Override
    public void setTarget(Destroyable destroyable) {
        target = destroyable;
    }
}
