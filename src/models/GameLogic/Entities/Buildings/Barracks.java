package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.NotEnoughResourcesException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.TrainingTroop;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;

public class Barracks extends Building {

    private ArrayList<TrainingTroop> trainingTroops;

    public Barracks(Position position, int number) {
        super(position, number);
    }

    public Barracks(ArrayList<TrainingTroop> trainingTroops, Position position, int number) {
        super(position, number);
        this.trainingTroops.addAll(trainingTroops);
    }

    public boolean hasSpace() {

    }

    public boolean trainNewTroop(TrainingTroop trainingTroop) {

    }

    public boolean removeTroop(String troopName) {

    }

    public ArrayList<TrainingTroop> getTrainingTroops() {
        return trainingTroops;
    }

    @Override
    public Resource getUpgradeResource() {
        int gold = (Integer) GameLogicConfig.getFromDictionary("BarracksUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        reduceTroopsTrainingTime(); //TODO create method
        this.setLevel(this.getLevel() + 1);
    }

    @Override
    public Bounty getBounty() {
        int gold = (int) GameLogicConfig.getFromDictionary("BarracksBuildGold");
        int elixir = 0;
        Resource resource = new Resource(gold, elixir);
        int score = this.score;
        return new Bounty(score, resource);
    }


}
