package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Exceptions.NotAvailableAtThisLevelException;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
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
        trainingTroops = new ArrayList<>();
    }

    public Barracks(ArrayList<TrainingTroop> trainingTroops, Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        trainingTroops = new ArrayList<>();
        this.trainingTroops.addAll(trainingTroops);
    }

    public boolean hasSpace() {
        return true;
        // TODO: 5/5/2018
    }

    public int getMaxAvailableTrainingTroop(String troopType, Resource stock) {
        return Resource.divideResources(stock, TrainingTroop.getTrainingResources(troopType));
    }

    public void trainNewTroop(String troopType) throws NotAvailableAtThisLevelException {
        if (troopType.equals("Dragon")) {
            if (level < 2) {
                throw new NotAvailableAtThisLevelException();
            }
        }
        TrainingTroop newTroop = new TrainingTroop(troopType, level);
        trainingTroops.add(newTroop);
    }

    public void removeTroop(String troopName) {
        // TODO: 5/5/2018
    }

    public ArrayList<TrainingTroop> getTrainingTroops() {
        return trainingTroops;
    }

    @Override
    public void upgrade() throws UpgradeLimitReachedException {
        int maxInitialConstructTime = 0;
        for (String key : GameLogicConfig.getClassPropertiesName()) {
            if (key.matches("TrainTime")) {
                maxInitialConstructTime = Math.max(maxInitialConstructTime, GameLogicConfig.getFromDictionary(key));
            }
        }
        if (level >= maxInitialConstructTime) {
            throw new UpgradeLimitReachedException();
        }
        setLevel(getLevel() + 1);
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
