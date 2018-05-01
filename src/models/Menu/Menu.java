package models.Menu;

import controllers.enums.CommandType;
import controllers.enums.DynamicListType;

import java.util.ArrayList;

public class Menu extends MenuItem {
    private String label;
    private Menu parent;
    private ArrayList<MenuItem> items;
    private ArrayList<MenuItem> dynamicItems;
    private DynamicListType dynamicListType;

    public Menu() {
        super(CommandType.OPEN_MENU);
        this.dynamicListType = DynamicListType.EMPTY;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

    public ArrayList<MenuItem> getDynamicItems() {
        return dynamicItems;
    }

    public void updateDynamicItems(ArrayList<MenuItem> dynamicItems) {
        this.dynamicItems = dynamicItems;
    }

    public DynamicListType getDynamicListType() {
        return dynamicListType;
    }

    public void setDynamicListType(DynamicListType dynamicListType) {
        this.dynamicListType = dynamicListType;
    }
}
