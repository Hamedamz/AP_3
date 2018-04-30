package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.TrainingTroop;

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
