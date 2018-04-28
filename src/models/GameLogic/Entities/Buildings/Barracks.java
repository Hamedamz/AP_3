package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Resource;
import models.GameLogic.TrainingTroop;

import java.util.ArrayList;

public class Barracks extends Building {

    private ArrayList<TrainingTroop> trainigTroops;
    private int size;

    public Barracks(int number) {

    }

    public boolean hasSpace() {

    }

    public boolean trainNewTroop(TrainingTroop trainingTroop) {

    }

    public boolean removeTroop(String troopName) {

    }

    public ArrayList<TrainingTroop> getTrainingTroops() {
        return trainigTroops;
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


}
