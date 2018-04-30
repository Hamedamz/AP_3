package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;

public class Cannon extends DefensiveBuilding {

    public Cannon(Position position, int number) {
        super(position, number);
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.AREA_SPLASH;
    }

    @Override
    public Resource getUpgradeResource() {
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }
}
