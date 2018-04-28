package models.GameLogic;

import models.GameLogic.Entities.Buildings.Building;

public class Builder {
    private int constructRemainingTime;
    private Building underConstructBuilding;

    public void startBuilding(String buildingType) {
        constructRemainingTime = 0; //fixme after implementing dictionary
        underConstructBuilding = null; // FIXME: 4/26/2018 after implementing dictionary
        //buildingType != townhall
    }

    public void build() {
        constructRemainingTime--;
        if (isBuildingFinished()) {
            underConstructBuilding.finishConstruct();
            underConstructBuilding = null;
        }
    }

    public boolean isBuilderBusy() {
        return underConstructBuilding == null;
    }

    public boolean isBuildingFinished() {
        return constructRemainingTime <= 0;
    }
}
