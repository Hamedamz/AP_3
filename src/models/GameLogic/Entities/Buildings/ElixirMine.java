package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;

public class ElixirMine extends Mine {

    public ElixirMine(Position position, int number) {
        super(position, number);
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
