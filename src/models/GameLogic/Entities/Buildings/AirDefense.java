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
    public Resource getUpgradeResource() {
        int gold = (Integer) GameLogicConfig.getFromDictionary("AirDefenseUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        this.hitPoints += (int) GameLogicConfig.getFromDictionary("AirDefenseUpgradeHitPointsAddition");
        this.damage += (int) GameLogicConfig.getFromDictionary("AirDefenseUpgradeDamageAddition");
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
