package models.Menu;

import controllers.enums.CommandType;

import java.util.ArrayList;

public class Menu extends MenuItem {
    private ArrayList<MenuItem> items;
    private Menu parent;

    public Menu() {
        super(CommandType.OPEN_MENU);
    }

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
