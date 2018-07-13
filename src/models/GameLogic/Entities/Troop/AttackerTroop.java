package models.GameLogic.Entities.Troop;

import interfaces.Destroyable;
import interfaces.MovingAttacker;
import models.GameLogic.BattleGround;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Defender;
import models.GameLogic.Exceptions.NoTargetFoundException;
import models.GameLogic.Position;
import models.GameLogic.enums.TroopTargetType;
import models.setting.GameLogicConfig;
import models.setting.GameLogicConstants;
import viewers.BattleGroundScene;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;

public abstract class AttackerTroop extends Troop implements MovingAttacker, Destroyable {
    protected TroopTargetType targetType;
    protected int maxHitPoints;
    protected int hitPoints;
    protected int range;
    protected int attackSpeed;
    protected int damage;
    protected Defender currentTarget;
    private boolean isInvulnerable = false;

    public AttackerTroop() {
        String className = this.getClass().getSimpleName();
        this.damage = GameLogicConfig.getFromDictionary(className + "Damage");
        this.range = GameLogicConfig.getFromDictionary(className + "Range");
        this.hitPoints = GameLogicConfig.getFromDictionary(className + "HitPoints");
        this.attackSpeed = GameLogicConfig.getFromDictionary(className + "AttackSpeed");
        this.attackCounter = attackSpeed;
        this.maxHitPoints = this.hitPoints;
    }

    public TroopTargetType getTargetType() {
        return targetType;
    }

    @Override
    public Destroyable getTarget() {
        return currentTarget;
    }

    private int attackCounter;

    @Override
    public void update(BattleGround battleGround, int turnPerSecond, int turn) {
        boolean isBigTurn = (turn % turnPerSecond == 0);
        attackCounter++;
        if (getTarget() == null || getTarget().isDestroyed() || battleGround.isWallDestroyed() || isBigTurn) {
            try {
                findTarget(battleGround);
            } catch (NoTargetFoundException e) {
                battleGround.setGameFinished(true);
            }
        }
        if(getPath() == null || isBigTurn) {
            findPath(battleGround);
        }
        if (((turn + 1) * getSpeed() / turnPerSecond) > (turn * getSpeed() / turnPerSecond)) {
            move();
        }
        if(attackCounter >= GameLogicConstants.DEFAULT_ATTACK_SPEED * turnPerSecond / getAttackSpeed()) {
            giveDamageTo(getTarget(), battleGround);
        }
    }

    @Override
    public void findTarget(BattleGround battleGround) throws NoTargetFoundException {
        double minDistance = Double.MAX_VALUE;
        Destroyable minDistantDestroyable = null;
        for (Destroyable destroyable : battleGround.getEnemyDefenders()) {
            if (!destroyable.isDestroyed() && TroopTargetType.isTroopTargetAppropriate(getTargetType(), (Defender) destroyable)) {
                double distance;
                if(destroyable instanceof Building) {
                    distance = this.getPosition().calculateDistanceFromBuilding(destroyable.getPosition(), ((Building) destroyable).getSize());
                } else {
                    distance = this.getPosition().calculateDistance(destroyable.getPosition());
                }
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistantDestroyable = destroyable;
                }
            }
        }
        if (minDistance < Double.MAX_VALUE) {
            currentTarget = (Defender) minDistantDestroyable;
            return;
        }

        for (Destroyable destroyable : battleGround.getEnemyDefenders()) {
            if (!destroyable.isDestroyed() && TroopTargetType.isTroopTargetAppropriate(TroopTargetType.BUILDING, (Defender) destroyable)) {
                double distance;
                if(destroyable instanceof Building) {
                    distance = this.getPosition().calculateDistanceFromBuilding(destroyable.getPosition(), ((Building) destroyable).getSize());
                } else {
                    distance = this.getPosition().calculateDistance(destroyable.getPosition());
                }
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistantDestroyable = destroyable;
                }
            }
        }

        if (minDistance < Double.MAX_VALUE) {
            currentTarget = (Defender) minDistantDestroyable;
            return;
        }
        throw new NoTargetFoundException();
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        int size = (destroyable instanceof Building) ? ((Building) destroyable).getSize() : 1;
        if (getPosition().calculateDistanceFromBuilding(destroyable.getPosition(), size) <= getEffectRange()) {
            attackCounter = 0;
            destroyable.takeDamageFromAttack(damage);
            switch (this.getClass().getSimpleName()) {
                case "Archer" :
                    SoundPlayer.play(Sounds.archerAttackSound);
                    break;
                case "Guardian" :
                    SoundPlayer.play(Sounds.barbarianAttackSound);
                    break;
                case "Dragon" :
                    SoundPlayer.play(Sounds.dragonAttackSound);
                    break;
                case "Giant" :
                    SoundPlayer.play(Sounds.giantAttackSound);
            }
            BattleGroundScene.getInstance().attackHappened(this, destroyable);
        }
    }

    @Override
    public void takeDamageFromAttack(int damage) {
        if (!isInvulnerable) hitPoints -= damage;
    }

    @Override
    public void destroy() {
        //really?
    }

    @Override
    public void upgrade() {
        setLevel(getLevel() + 1);
        damage += GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeDamageAddition");
        hitPoints += GameLogicConfig.getFromDictionary(getClass().getSimpleName() + "UpgradeHitPointsAddition");
    }

    @Override
    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    @Override
    public int getEffectRange() {
        return range * Position.CELL_SIZE;
    }

    public void heal(int amount) {
        hitPoints += amount;
        if (hitPoints > maxHitPoints) {
            hitPoints = maxHitPoints;
        }
    }

    @Override
    public void removeTarget() {
        currentTarget = null;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public void setTarget(Destroyable destroyable) {
        currentTarget = (Defender) destroyable;
    }

    public void setInvulnerable(boolean invulnerable) {
        isInvulnerable = invulnerable;
    }
}

