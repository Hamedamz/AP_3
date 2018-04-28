package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Resource;

public class GoldMine extends Mine {

    public GoldMine(int number) {

    }

    @Override
    public Resource produce() {
        return new Resource(productionRate, 0);
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
