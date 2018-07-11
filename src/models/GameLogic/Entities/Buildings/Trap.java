package models.GameLogic.Entities.Buildings;

import interfaces.Destroyable;
import models.GameLogic.BattleGround;
import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.utills.IDGenerator;
import models.setting.GameLogicConfig;

public class Trap extends DefensiveBuilding {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 13);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 13);

    private boolean isRearmed = true;

    public Trap(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.AREA_SPLASH;
    }


    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("TrapBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }


    @Override
    public void destroy() {
        super.destroy();
        isRearmed = false;
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed() && isRearmed;
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        super.giveDamageTo(destroyable, battleGround);
        hitPoints = -10000;
    }
}
