package models.GameLogic;

import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Entity;

import java.util.ArrayList;

public class Map {
    // TODO: 4/12/2018 add map size to dic
    private int width, length;
    private ArrayList<Building> buildings;
    private boolean[][] isOccupied;

    public Map() {
        buildings = new ArrayList<>();
    }

    public void addNewBuilding(Building building) {
        buildings.add(building);
    }

    public boolean isOccupied(int i, int j) {
        return isOccupied[i][j];
    }

    public void occupy(int i, int j) {
        isOccupied[i][j] = true;
    }

    public Map clone() {

    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

}
