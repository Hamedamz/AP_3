package models.GameLogic.Entities.Buildings;

import interfaces.Destroyable;
import models.GameLogic.BattleGround;
import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.Position;
import models.GameLogic.enums.BuildingDamageType;
import models.GameLogic.enums.BuildingTargetType;
import models.ID;
import models.IDGenerator;

public class Trap extends DefensiveBuilding {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 13);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 13);

    private boolean isRearmed = true;

    public Trap(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        this.targetType = BuildingTargetType.GROUND;
        this.damageType = BuildingDamageType.AREA_SPLASH;
    }

    @Override
    public Bounty getBounty() {
        // TODO: 6/24/2018 arshia moghimi
    }


    @Override
    public void destroy() {
        super.destroy();
        isRearmed = false;
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed() && isRearmed;
    }

    @Override
    public void giveDamageTo(Destroyable destroyable, BattleGround battleGround) {
        super.giveDamageTo(destroyable, battleGround);
        hitPoints = -10000;
    }
}
