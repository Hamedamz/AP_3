package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;

public class ElixirStorage extends Storage {

    public ElixirStorage(Position position, int number) {
        super(position, number);
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
