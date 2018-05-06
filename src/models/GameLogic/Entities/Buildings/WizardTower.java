package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

public class WizardTower extends DefensiveBuilding {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", );
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", );

    public WizardTower(Position position, int number) {
        super(position, number);
        this.targetType = BuildingTargetType.GROUND_AND_AIR;
        this.damageType = BuildingDamageType.AREA_SPLASH;
    }

    @Override
    public Resource getUpgradeResource() {
        int gold = (Integer) GameLogicConfig.getFromDictionary("WizardTowerUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        this.hitPoints += (int) GameLogicConfig.getFromDictionary("WizardTowerUpgradeHitPointsAddition");
        this.damage += (int) GameLogicConfig.getFromDictionary("WizardTowerUpgradeDamageAddition");
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("WizardTowerBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }
}
