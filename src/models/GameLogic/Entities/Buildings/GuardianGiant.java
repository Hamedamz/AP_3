package models.GameLogic.Entities.Buildings;

import models.GameLogic.Entities.Troop.AttackerTroop;
import models.GameLogic.Entities.Troop.Troop;
import models.interfaces.Destroyable;
import models.interfaces.MovingAttacker;
import models.GameLogic.BattleGround;
import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.enums.MoveType;
import models.GameLogic.utills.PathFinder;
import models.GameLogic.utills.IDGenerator;
import models.setting.GameLogicConfig;
import models.setting.GameLogicConstants;
import viewers.AppGUI;

import java.util.ArrayList;

public class GuardianGiant extends DefensiveBuilding implements MovingAttacker {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 14);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 14);

    public GuardianGiant(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        initialPosition = new Position(position.getX(), position.getY());
        speed = GameLogicConfig.getFromDictionary("GuardianGiantSpeed");
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.SINGLE_TARGET;
        this.attentionRange = GameLogicConfig.getFromDictionary("GuardianGiantAttentionRange");
    }

    private Position initialPosition;

    private int speed;
    private ArrayList<Position> movementPath;

    private int attentionRange;

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
        return MoveType.JUMPER;
    }

    @Override
    public void update(BattleGround battleGround, int turnPerSecond, int turn) {
        boolean isBigTurn = (turn % turnPerSecond == 0);
        attackCounter++;
        if (getTarget() == null || getTarget().isDestroyed() || isBigTurn) {
            try {
                findTarget(battleGround);
            } catch (NoTargetFoundException e) {
                return;
            }
        }
        if(getTarget() != null) {
            if (((turn + 1) * getSpeed() / turnPerSecond) >
                    (turn * getSpeed() / turnPerSecond)) {
                findPath(battleGround);
                move();
            }
        }

        if (!isDestroyed()) {
            if (attackCounter >= GameLogicConstants.DEFAULT_ATTACK_SPEED * turnPerSecond / getAttackSpeed()) {
                giveDamageTo(getTarget(), battleGround);
                target = null;
            }
        }
    }

    @Override
    public void findTarget(BattleGround battleGround) throws NoTargetFoundException {
        if(target != null) {
            if(target.isDestroyed() || target.getPosition().calculateDistanceFromBuilding(getPosition(), getSize()) > getAttentionRange()) {
                target = null;
            }
        }
        if(target == null) {
            double minDistance = Double.MAX_VALUE;
            Destroyable minDistanceDestroyable = null;
            for (Troop troop : battleGround.getDeployedTroops()) {
                if(troop instanceof Destroyable) {
                    Destroyable destroyable = (Destroyable) troop;
                    if (!destroyable.isDestroyed() && BuildingTargetType.isBuildingTargetAppropriate(this, (AttackerTroop) destroyable)) {
                        double distance = this.getPosition().calculateDistance(destroyable.getPosition());
                        if (distance < minDistance) {
                            minDistance = distance;
                            minDistanceDestroyable = destroyable;
                        }
                    }

                }
            }
            if (minDistance <= this.getAttentionRange()) {
                this.target = minDistanceDestroyable;
                return;
            }
        }
        throw new NoTargetFoundException();
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        if(destroyable.getPosition().calculateDistanceFromBuilding(this.getPosition(), getSize()) <= getEffectRange()) {
            super.giveDamageTo(destroyable, battleGround);
            // TODO: 7/16/2018 GuardianGiant attackListener
        }
    }

    @Override
    public void findPath(BattleGround battleGround) {
        movementCounter = 0;
        movementPath = PathFinder.getPath(battleGround.getEnemyGameMap(), this, target.getPosition(), getEffectRange());
    }

    @Override
    public void move() {
        movementCounter++;
        int dir = calculateDirection();
        setPosition(getNextPosition());
        AppGUI.getBattleGroundScene().movementHappened(dir, this);
    }

    public Position getNextPosition() {
        return this.movementPath.get(Math.min(movementCounter, getPath().size() - 1));
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
    public ArrayList<Position> getPath() {
        return movementPath;
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


    public int getAttentionRange() {
        return attentionRange * Position.CELL_SIZE;
    }
}
