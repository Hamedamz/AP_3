package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

public class GoldMine extends Mine {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 1);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 1);

    public GoldMine(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
    }

    @Override
    public Resource produce() {
        return new Resource(productionRate, 0);
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        int productionRateAddition = (int) GameLogicConfig.getFromDictionary("GoldMineUpgradeProductionRateAddition");
        this.productionRate += (this.productionRate * productionRateAddition) / 100;
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("GoldMineBuildGold");
        int elixir = (int) GameLogicConfig.getFromDictionary("GoldMineBuildElixir");
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }

}
