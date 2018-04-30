package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Builder;
import models.GameLogic.Resource;

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
        return null;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public Bounty getBounty() {
        return null;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
