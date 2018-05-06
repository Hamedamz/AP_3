package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.ID;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

public class GoldStorage extends Storage {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", );
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", );

    public GoldStorage(Position position, ID id) {
        super(position, id);
    }

    @Override
    public Resource getUpgradeResource() {
        int gold = (Integer) GameLogicConfig.getFromDictionary("GoldStorageUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
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
