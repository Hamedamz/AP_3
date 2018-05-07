package models.Menu;

import controllers.enums.*;
import models.GameLogic.Entities.Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu extends MenuItem {
    private String label;
    private ArrayList<MenuItem> items;
    private HashMap<DynamicMenuItem, String> dynamicItems;
    private DynamicListType dynamicListType;
    private Entity model;

    public Menu() {
        super(CommandType.OPEN_MENU);
        this.dynamicListType = DynamicListType.EMPTY;
        items = new ArrayList<>();
        dynamicItems = new HashMap<>();
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

    public ArrayList<DynamicMenuItem> getDynamicItems() {
        return new ArrayList<>(dynamicItems.keySet());
    }

    public HashMap<DynamicMenuItem, String> getDynamicItemsHashMap() {
        return dynamicItems;
    }

    public void updateDynamicItems(HashMap<DynamicMenuItem, String> dynamicItems) {
        this.dynamicItems = dynamicItems;
    }

    public DynamicListType getDynamicListType() {
        return dynamicListType;
    }

    public void setDynamicListType(DynamicListType dynamicListType) {
        this.dynamicListType = dynamicListType;
    }

    public Entity getModel() {
        return model;
    }

    public void setModel(Entity model) {
        this.model = model;
    }
}