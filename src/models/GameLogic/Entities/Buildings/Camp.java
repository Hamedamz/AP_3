package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Resource;
import models.GameLogic.TrainingTroop;

import java.util.ArrayList;

public class Camp extends Building {

    private ArrayList<Troop> troops;
    private int size;

    public Camp(int number) {

    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addTroop(TrainingTroop trainingTroop) {

    }

    public void removeTroop(String troopName) {

    }

    public boolean hasSpace() {

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

    @Override
    public void performLosses() {
        troops = new ArrayList<>();
    }
}
