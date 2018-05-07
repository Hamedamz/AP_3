package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.ID;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

public class AirDefense extends DefensiveBuilding {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 10);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 10);

    public AirDefense(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID() );
        this.targetType = BuildingTargetType.AIR;
        this.damageType = BuildingDamageType.SINGLE_TARGET;
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("AirDefenseBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }
}
