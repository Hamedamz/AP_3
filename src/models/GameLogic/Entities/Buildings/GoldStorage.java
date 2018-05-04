package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.Setting.GameLogicConfig;

public class GoldStorage extends Storage {

    public GoldStorage(Position position, int number) {
        super(position, number);
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
