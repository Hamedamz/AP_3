package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Resource;

public class ElixirMine extends Mine {

    private int productionRate;

    public ElixirMine(int number) {
    }

    @Override
    public Resource produce() {
        return new Resource(0, productionRate);
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
