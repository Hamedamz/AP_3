package models;

import java.util.ArrayList;

public class Menu extends MenuItem {
    private ArrayList<MenuItem> items;
    private Menu parent;

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }
}
