package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NotEnoughCapacityException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.TrainingTroop;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.Comparator;

public class Camp extends Building {

    private ArrayList<Troop> troops;
    private int size;

    public Camp(Position position, int number) {
        super(position, number);
        String className = this.getClass().getName();
        this.size = (Integer) GameLogicConfig.getFromDictionary(className + "Capacity");
    }

    public Camp(ArrayList<Troop> troops, Position position, int number) {
        super(position, number);
        this.troops.addAll(troops);
        String className = this.getClass().getName();
        this.size = (Integer) GameLogicConfig.getFromDictionary(className + "Capacity");
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addTroop(Troop troop) throws NotEnoughCapacityException {
        if (!hasSpace()) {
            throw new NotEnoughCapacityException();
        }
        troops.add(troop);
    }

    public void removeTroop(String troopName) {

    }

    public boolean hasSpace() {
        return size > troops.size();
    }

    public static class CampComparator implements Comparator<Camp> {
        @Override
        public int compare(Camp o1, Camp o2) {
            return o1.troops.size() - o2.troops.size();
        }
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
