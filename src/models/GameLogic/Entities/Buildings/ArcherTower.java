package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.GameLogic.utills.IDGenerator;
import models.Setting.GameLogicConfig;

public class ArcherTower extends DefensiveBuilding {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 8);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 8);

    public ArcherTower(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.SINGLE_TARGET;
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("ArcherTowerBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }
}
