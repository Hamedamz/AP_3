package models.GameLogic;

import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.TownHall;
import models.GameLogic.Entities.Entity;

import java.util.ArrayList;

public class Map extends Entity {
    // TODO: 4/12/2018 add map size to dic
    private int width, height;
    private ArrayList<Building> buildings;
    private boolean[][] isOccupied;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        buildings = new ArrayList<>();
        isOccupied = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            isOccupied[0][i] = true;
            isOccupied[i][0] = true;
            isOccupied[i][height - 1] = true;
            isOccupied[width - 1][i] = true;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addNewBuilding(Building building) {
        buildings.add(building);
        int x = building.getPosition().getX();
        int y = building.getPosition().getY();
        setOccupied(x, y);
        if (building instanceof TownHall) {
            setOccupied(x + 1, y + 1);
            setOccupied(x + 1, y);
            setOccupied(x, y + 1);
        }
    }

    public boolean isOccupied(int i, int j) {
        return isOccupied[i][j];
    }

    public void setOccupied(int x, int y) {
        isOccupied[x][y] = true;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
        for (Building building : buildings) {
            setOccupied(building.getPosition().getX(), building.getPosition().getY());
        }
    }

    public void constructBuilding(int i, int j) {
        setOccupied(i, j);
    }


    public Map clone() {
        return null;
        // TODO: 5/5/2018
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

}
