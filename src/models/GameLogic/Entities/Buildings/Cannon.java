package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.Setting.GameLogicConfig;

public class Cannon extends DefensiveBuilding {

    public Cannon(Position position, int number) {
        super(position, number);
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.AREA_SPLASH;
    }

    @Override
    public Resource getUpgradeResource() {
        int gold = (Integer) GameLogicConfig.getFromDictionary("CannonUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        this.hitPoints += (int) GameLogicConfig.getFromDictionary("CannonUpgradeHitPointsAddition");
        this.damage += (int) GameLogicConfig.getFromDictionary("CannonUpgradeDamageAddition");
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
