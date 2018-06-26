package models.GameLogic.Entities.Buildings;

import interfaces.MovingAttacker;
import models.GameLogic.BattleGround;
import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.enums.MoveType;
import models.ID;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

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
        // TODO: 6/24/2018 arshia moghimi
    }

    @Override
    public void move() {
        this.position = this.movementPath.get(Math.min(speed, getPath().size() - 1));
    }

    @Override
    public MoveType getTroopMoveType() {
        return MoveType.GROUND;
    }

    @Override
    public void findPath(BattleGround battleGround) {

    }

    @Override
    public ArrayList<Position> getPath() {
        return null;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public void resetPosition() {
        position = initialPosition;
    }
}
