package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.NotEnoughResourcesException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.TrainingTroop;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;

public class Barracks extends Building {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 6);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 6);

    private ArrayList<TrainingTroop> trainingTroops;

    public Barracks(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
    }

    public Barracks(ArrayList<TrainingTroop> trainingTroops, Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        this.trainingTroops.addAll(trainingTroops);
    }

    public boolean hasSpace() {
        return true;
        // TODO: 5/5/2018
    }

    public boolean trainNewTroop(TrainingTroop trainingTroop) {
        return true;
        // TODO: 5/5/2018
    }

    public boolean removeTroop(String troopName) {
        return true;
        // TODO: 5/5/2018
    }

    public ArrayList<TrainingTroop> getTrainingTroops() {
        return trainingTroops;
    }

    @Override
    public Resource getUpgradeResource() {
        int gold = (int) GameLogicConfig.getFromDictionary("BarracksUpgradeGold");
        int elixir = 0;
        return new Resource(gold, elixir);
    }

    @Override
    public void upgrade() {
        //reduceTroopsTrainingTime(); //TODO create method
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
