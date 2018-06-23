package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.Position;
import models.IDGenerator;

public class Wall extends Building {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 12);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 12);

    public Wall(Position position, boolean isFriendly){
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
    }

    @Override
    public void upgrade() throws UpgradeLimitReachedException {
        super.upgrade();
        // TODO: 6/24/2018 arshia moghimi 
    }

    @Override
    public Bounty getBounty() {
        // TODO: 6/24/2018  arshia moghimi
    }
}
