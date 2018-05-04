package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.Setting.GameLogicConfig;

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
        int gold = (Integer) GameLogicConfig.getFromDictionary("ElixirMineUpgradeGold");
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
