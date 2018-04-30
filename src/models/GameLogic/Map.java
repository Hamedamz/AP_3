package models.GameLogic;

import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Entity;

import java.util.ArrayList;

public class Map {
    // TODO: 4/12/2018 add map size to dic

    private ArrayList<Building> buildings;


    public Map() {
        buildings = new ArrayList<>();
    }

    public void addNewBuilding(Building building) {
        buildings.add(building);
    }

    public Map clone() {

    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

}
