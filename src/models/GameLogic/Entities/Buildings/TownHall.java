package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Builder;
import models.GameLogic.Exceptions.NoFreeBuilderException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;

public class TownHall extends Storage {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 5);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 5);

    private ArrayList<Builder> builders;
    private int townHallScore;

    public TownHall(Position position, boolean isFriendly){
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID() );
        townHallScore = 0;
        builders = new ArrayList<>();
        builders.add(new Builder());
    }

    public ArrayList<Builder> getBuilders() {
        return builders;
    }

    public int getTownHallScore() {
        return townHallScore;
    }

    @Override
    public Resource getUpgradeResource() {
        int gold = (int) GameLogicConfig.getFromDictionary("TownHallUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        if (level % 5 == 0) {
            builders.add(new Builder());
        }
        this.setLevel(this.getLevel() + 1);
        int hitPointsAddition = (int) GameLogicConfig.getFromDictionary("TownHallUpgradeHitPointsAddition");
        this.setHitPoints(this.hitPoints + hitPointsAddition);
    }

    @Override
    public Bounty getBounty() {
        int gold = 1000;
        int elixir = 500;
        Resource resource = new Resource(gold, elixir);
        int score = this.townHallScore;
        return new Bounty(score, resource);
    }

    public void addScore(int score) {
        this.townHallScore += score;
    }


    public Builder getFreeBuilder() throws NoFreeBuilderException {
        for (Builder builder : builders) {
            if (!builder.isBuilderBusy()) {
                return builder;
            }
        }
        throw new NoFreeBuilderException();
    }
}
