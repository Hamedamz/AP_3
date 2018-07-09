package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.utills.IDGenerator;
import models.Setting.GameLogicConfig;

public class WizardTower extends DefensiveBuilding {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 11);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 11);

    public WizardTower(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        this.targetType = BuildingTargetType.GROUND_AND_AIR;
        this.damageType = BuildingDamageType.AREA_SPLASH;
        this.size = 1;
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
