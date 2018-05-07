package models.GameLogic;

import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Exceptions.NoFreeBuilderException;
import models.Setting.GameLogicConfig;

public class Builder {
    private int constructRemainingTime;
    private Building underConstructBuilding;

    public void startBuilding(Building building) {
        constructRemainingTime = GameLogicConfig.getFromDictionary(building.getClass().getSimpleName() + "TrainTime");
        underConstructBuilding = building;
    }

    public void build() {
        constructRemainingTime--;
        if (isBuildingFinished()) {
            underConstructBuilding.finishConstruct();
            underConstructBuilding = null;
        }
    }

    public Building getUnderConstructBuilding() {
        return underConstructBuilding;
    }

    public int getConstructRemainingTime() {
        return constructRemainingTime;
    }

    public boolean isBuilderBusy() {
        return underConstructBuilding != null;
    }

    public boolean isBuildingFinished() {
        return constructRemainingTime <= 0;
    }
}
