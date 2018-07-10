package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.utills.IDGenerator;
import models.setting.GameLogicConfig;

public class Cannon extends DefensiveBuilding {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 9);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 9);

    public Cannon(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.AREA_SPLASH;
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("CannonBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }
}
