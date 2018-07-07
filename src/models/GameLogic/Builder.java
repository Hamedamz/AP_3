package models.GameLogic;

import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Exceptions.NoFreeBuilderException;
import models.Setting.GameLogicConfig;

public class Builder {
    private int constructRemainingTime;
    private int totalConstructionTime;
    private Building underConstructBuilding;

    public void startBuilding(Building building) {
        constructRemainingTime = GameLogicConfig.getFromDictionary(building.getClass().getSimpleName() + "BuildTime");
        totalConstructionTime = constructRemainingTime;
        underConstructBuilding = building;
    }

    public void build() {
        if (underConstructBuilding == null)
            return;
        constructRemainingTime--;
        if (isBuildingFinished()) {
            underConstructBuilding.finishConstruct();
            underConstructBuilding = null;
        }
    }

    public int getTotalConstructionTime() {
        return totalConstructionTime;
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

    public void finishBuilding() {
        if (underConstructBuilding == null)
            return;
        constructRemainingTime = 0;
        underConstructBuilding.finishConstruct();
        underConstructBuilding = null;

    }
}
