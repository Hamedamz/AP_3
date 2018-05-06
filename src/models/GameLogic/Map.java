package models.GameLogic;

import models.GameLogic.Entities.Buildings.Building;

import java.util.ArrayList;

public class Map {
    // TODO: 4/12/2018 add map size to dic
    private int width, height;
    private ArrayList<Building> buildings;
    private boolean[][] isOccupied;


    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        buildings = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addNewBuilding(Building building) {
        buildings.add(building);
    }

    public boolean isOccupied(int i, int j) {
        return isOccupied[i][j];
    }

    public void constructBuilding(int i, int j) {
        isOccupied[i][j] = true;
    }


    public Map clone() {
        return null;
        // TODO: 5/5/2018
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

}
