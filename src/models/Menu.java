package models;

import interfaces.Item;

import java.util.ArrayList;

public class Menu implements Item {
    private String name;
    private ArrayList<Item> items;
    private Menu parent;

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public void addItem (Item item) {
        items.add(item);
    }

    @Override
    public void action() {

    }
}

abstract class Button implements Item {
    private String label;

    public Button(String name) {
        this.label = name;
    }

    public String getLabel() {
        return label;
    }
}

class MenuBuilder {
    public Menu BuildMainMenu() {
        Menu menu = new Menu();
        return menu;
    }
}