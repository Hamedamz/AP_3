package models.multiPlayer.battleManger;

import models.GameLogic.Bounty;
import models.GameLogic.Entities.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class WarLog implements Serializable {
    private int time;
    private Bounty bounty;
    private ArrayList<Entity> destroyedEntities;

    public WarLog(int time, Bounty bounty) {
        this.time = time;
        this.bounty = bounty;
        destroyedEntities = new ArrayList<>();
    }

    public void addLog(Entity entity) {
        destroyedEntities.add(entity);
    }

    public Bounty getBounty() {
        return bounty;
    }

    public ArrayList<Entity> getDestroyedEntities() {
        return destroyedEntities;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {

        return time;
    }
}
