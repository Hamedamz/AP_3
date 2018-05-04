package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Builder;
import models.GameLogic.Exceptions.NoFreeBuilderException;
import models.GameLogic.Resource;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;

public class TownHall extends ResourceBuilding {

    private ArrayList<Builder> builders;
    private int score;

    public TownHall() {
        //TODO complete this
    }

    public ArrayList<Builder> getBuilders() {
        return builders;
    }

    public int getScore() {
        return score;
    }

    @Override
    public Resource getUpgradeResource() {
        int gold = (int) GameLogicConfig.getFromDictionary("TownHallUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        int hitPointsAddition = (int) GameLogicConfig.getFromDictionary("TownHallUpgradeHitPointsAddition");
        this.setHitPoints(this.hitPoints + hitPointsAddition);
    }

    @Override
    public Bounty getBounty() {
        int gold = 1000;
        int elixir = 500;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }

    public void addScore(int score) {
        this.score += score;
    }


    public Builder getFreeBuilder() throws NoFreeBuilderException {
        for(Builder builder : builders) {
            if (!builder.isBuilderBusy()) {
                return builder;
            }
        }
        throw new NoFreeBuilderException();
    }
}
