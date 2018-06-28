package viewers.menu;

import controllers.enums.DynamicListType;

import java.util.ArrayList;

public final class MenuBuilder {
    private String label;
    private DynamicListType dynamicListType;
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
        for (MenuItem menuItem : this.items) {
            if (menuItem.getLabel().equals(item.getLabel())) {
                this.items.remove(menuItem);
                this.items.add(item);
                return this;
            }
        }
        this.items.add(item);
        return this;
    }

    public MenuBuilder withDynamicList(DynamicListType dynamicListType) {
        this.dynamicListType = dynamicListType;
        return this;
    }

    public Menu build() {
        Menu menu = new Menu();
        menu.setLabel(label);
        menu.setItems(items);
        if (dynamicListType != null) {
            menu.setDynamicListType(dynamicListType);
        }
        return menu;
    }
}
