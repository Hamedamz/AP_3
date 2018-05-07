package models.GameLogic;

import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NotEnoughCapacityException;
import models.Setting.GameLogicConfig;

public class TrainingTroop {
    private int timeRemaining;
    private Resource trainingCost; //extra feature
    private int level;
    private Troop troop;

    public TrainingTroop(String troopType, int barracksLevel) {
        int buildElixir = (int) GameLogicConfig.getFromDictionary(troopType + "BuildElixir");
        trainingCost = new Resource(0, buildElixir);
        level = barracksLevel;
        troop = Troop.castStringToTroopType(troopType);
        timeRemaining = (int) GameLogicConfig.getFromDictionary(troopType + "BuildTime");
        timeRemaining -= level;
        if (timeRemaining < 0) {
            timeRemaining = 0;
        }
    }

    public static Resource getTrainingResources(String troopType) {
        int buildElixir = (int) GameLogicConfig.getFromDictionary(troopType + "BuildElixir");
        return new Resource(0, buildElixir);
    }


    public int getTimeRemaining() {
        return timeRemaining;
    }

    public Resource getTrainingCost() {
        return trainingCost;
    }

    public void update() {
        timeRemaining--;
    }

    public boolean hasEndedTraining() {
        return timeRemaining <= 0;
    }
    
    public void moveToCamp(Camp camp) throws NotEnoughCapacityException {
        Troop trainedTroop = this.convertToTroop();
        camp.addTroop(trainedTroop);
        trainedTroop.setTroopCamp(camp);
    }

    public Troop convertToTroop() {
        for (int i = 0; i < level; i++) {
            troop.upgrade();
        }
        // TODO: 5/3/2018 i don't know whether it is level - 1 or level
        return troop;
    }

//    public int getSpace() {
//
//    }
}
