package models;

import java.util.ArrayList;

public class Menu {
    private String name;
    private ArrayList<MenuItem> items;
    private Menu parent;

    public ArrayList<MenuItem> getItems() {
        return items;
    }
}