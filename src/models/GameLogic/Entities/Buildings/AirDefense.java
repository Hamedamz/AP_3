package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;

public class AirDefense extends DefensiveBuilding {

    public AirDefense(Position position, int number) {
        super(position, number);
        this.targetType = BuildingTargetType.AIR;
        this.damageType = BuildingDamageType.SINGLE_TARGET;
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
