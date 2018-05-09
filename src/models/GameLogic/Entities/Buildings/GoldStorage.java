package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.ID;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

public class GoldStorage extends Storage {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 3);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 3);

    public GoldStorage(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
    }

    @Override
    public void upgrade() throws UpgradeLimitReachedException {
        super.upgrade();
        int capacityAddition = (int) GameLogicConfig.getFromDictionary("GoldStorageUpgradeCapacityAddition");
        this.capacity.setGold(this.capacity.getGold() + (this.capacity.getGold() * capacityAddition) / 100);
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("GoldStorageBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        resource.addToThisResource(this.stock);
        int score = this.score;
        return new Bounty(score, resource);
    }
}
