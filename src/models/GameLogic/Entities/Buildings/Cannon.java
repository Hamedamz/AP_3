package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

public class Cannon extends DefensiveBuilding {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 9);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 9);

    public Cannon(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.AREA_SPLASH;
    }


}
