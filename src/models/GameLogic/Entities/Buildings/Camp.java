package models.GameLogic.Entities.Buildings;

import models.GameLogic.Bounty;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Exceptions.NotEnoughCapacityException;
import models.GameLogic.Exceptions.UpgradeLimitReachedException;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.GameLogic.TrainingTroop;
import models.ID;
import models.IDGenerator;
import models.Setting.GameLogicConfig;

import java.util.ArrayList;
import java.util.Comparator;

public class Camp extends Building {

    private static final IDGenerator friendlyIDGenerator = new IDGenerator("01", 7);
    private static final IDGenerator hostileIDGenerator = new IDGenerator("02", 7);

    private ArrayList<Troop> troops;
    private int size;

    public Camp(Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        String className = this.getClass().getSimpleName();
        this.size = (int) GameLogicConfig.getFromDictionary(className + "Capacity");
    }

    public Camp(ArrayList<Troop> troops, Position position, boolean isFriendly) {
        super(position, isFriendly ? friendlyIDGenerator.getNewID() : hostileIDGenerator.getNewID());
        this.troops.addAll(troops);
        String className = this.getClass().getSimpleName();
        this.size = (int) GameLogicConfig.getFromDictionary(className + "Capacity");
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
    public Resource getUpgradeResource() throws UpgradeLimitReachedException {
        throw new UpgradeLimitReachedException();
    }

    @Override
    public void upgrade() throws UpgradeLimitReachedException {
        throw new UpgradeLimitReachedException();
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
