package models.Menu;

import java.util.ArrayList;

public final class MenuBuilder {
    String label;
    private ArrayList<MenuItem> items;

    private MenuBuilder() {
        items = new ArrayList<>();
    }

    public static MenuBuilder aMenu() {
        return new MenuBuilder();
    }

    public static MenuBuilder aMenuExtending(Menu existingMenu) {
        MenuBuilder menuBuilder = new MenuBuilder();
        menuBuilder.label = existingMenu.getLabel();
        menuBuilder.items = new ArrayList<>(existingMenu.getItems());
        return menuBuilder;
    }

    public MenuBuilder withLabel(String label) {
        this.label = label;
        return this;
    }

    public MenuBuilder withItem(MenuItem item) {
        this.items.add(item);
        return this;
    }

    public Menu build() {
        Menu menu = new Menu();
        menu.setLabel(label);
        menu.setItems(items);
        return menu;
    }
}
