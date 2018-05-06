package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

public class ElixirMine extends Mine {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 2);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 2);

    public ElixirMine(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
    }

    @Override
    public Resource produce() {
        return new Resource(0, productionRate);
    }


    @Override
    public Resource getUpgradeResource() {
        int gold = (int) GameLogicConfig.getFromDictionary("ElixirMineUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        int productionRateAddition = (int) GameLogicConfig.getFromDictionary("ElixirMineUpgradeProductionRateAddition");
        this.productionRate += (this.productionRate * productionRateAddition) / 100;
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("ElixirMineBuildGold");
        int elixir = (int) GameLogicConfig.getFromDictionary("ElixirMineBuildElixir");
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }
}
