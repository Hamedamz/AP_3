package models;

import java.util.*;

public class Village {
    private ArrayList<Building> buildings;
    private Map map;
    private int score;

    public Village() {
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public Map getMap() {
        return map;
    }

    public int getScore() {
        return score;
    }

    public boolean addResources() {
        //it's false when we don't have enough capacity
    }

    
}
