package models.Menu;

import java.util.ArrayList;

public final class MenuBuilder {
    private ArrayList<MenuItem> items;
    private Menu parent;

    private MenuBuilder() {
        items = new ArrayList<>();
    }

    public static MenuBuilder aMenu() {
        return new MenuBuilder();
    }

    public MenuBuilder withItem(MenuItem item) {
        this.items.add(item);
        return this;
    }

    public MenuBuilder withParent(Menu parent) {
        this.parent = parent;
        return this;
    }

    public Menu build() {
        Menu menu = new Menu();
        menu.setItems(items);
        menu.setParent(parent);
        return menu;
    }
}
