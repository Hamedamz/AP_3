package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.utills.IDGenerator;
import models.Setting.GameLogicConfig;

public class ElixirStorage extends Storage {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 4);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 4);

    public ElixirStorage(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
    }

    @Override
    public void upgrade() throws UpgradeLimitReachedException {
        super.upgrade();
        int capacityAddition = (int) GameLogicConfig.getFromDictionary("ElixirStorageUpgradeCapacityAddition");
        this.capacity.setElixir(this.capacity.getElixir() + (this.capacity.getElixir() * capacityAddition) / 100);
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("ElixirStorageBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        resource.addToThisResource(this.stock);
        int score = this.score;
        return new Bounty(score, resource);
    }
}
