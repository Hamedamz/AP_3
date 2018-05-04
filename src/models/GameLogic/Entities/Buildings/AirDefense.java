package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.Setting.GameLogicConfig;

public class AirDefense extends DefensiveBuilding {

    public AirDefense(Position position, int number) {
        super(position, number);
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
