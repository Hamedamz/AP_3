package models.Menu;

import controllers.enums.*;
import models.GameLogic.Entities.Entity;

import java.util.ArrayList;

public class Menu extends MenuItem {
    private String label;
    private ArrayList<MenuItem> items;
    private ArrayList<DynamicMenuItem> dynamicItems;
    private DynamicListType dynamicListType;
    private Entity model;

    public Menu() {
        super(CommandType.OPEN_MENU);
        this.dynamicListType = DynamicListType.EMPTY;
        items = new ArrayList<>();
        dynamicItems = new ArrayList<>();
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
        return dynamicItems;
    }

    public void updateDynamicItems(ArrayList<DynamicMenuItem> dynamicItems) {
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
