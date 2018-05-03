package models.GameLogic;

import models.GameLogic.Entities.Buildings.Camp;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NotEnoughCapacityException;

public class TrainingTroop {
    private int timeRemaining;
    private Resource buildingResource;
    private String troopType;
    private int level;

    public TrainingTroop(String troopType, int barracksLevel) {
        level = barracksLevel;
        this.troopType = troopType;
        timeRemaining = 0; //fixme dictionary
        timeRemaining -= level;
    }


    public int getTimeRemaining() {
        return timeRemaining;
    }

    public Resource getBuildingResource() {
        return buildingResource;
    }

    public String getTroopType() {
        return troopType;
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
        // TODO: 5/2/2018
    }

//    public int getSpace() {
//        // TODO: 4/26/2018
//    }
}
