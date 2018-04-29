package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Resource;

public class GoldStorage extends Storage {

    public GoldStorage(int number) {

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
