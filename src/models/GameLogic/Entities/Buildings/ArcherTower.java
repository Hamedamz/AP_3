package models.GameLogic.Entities.Buildings;

import javafx.geometry.Pos;
import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;

public class ArcherTower extends DefensiveBuilding {
    public ArcherTower(Position position, int number) {
        super(position, number);
        this.targetType = BuildingTargetType.GROUND_AND_AIR;
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
