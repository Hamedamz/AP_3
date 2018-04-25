package models.GameLogic;

import java.util.ArrayList;

public class Map {
    // TODO: 4/12/2018 add map size to dic

    private ArrayList<Entity> entities;
    private ArrayList<Building> buildings;


    public Map() {
        entities = new ArrayList<>();
        buildings = new ArrayList<>();
    }

    public void addNewBuilding(Building building) {
        addNewEntity(building);
        buildings.add(building);
    }

    public void addNewEntity(Entity entity) {
        entities.add(entity);
    }
    public Map clone() {

    }

}
