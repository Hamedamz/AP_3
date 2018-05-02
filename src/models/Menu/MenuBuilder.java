package models.Menu;

import controllers.enums.CommandType;
import controllers.enums.DynamicListType;

import java.util.ArrayList;

public final class MenuBuilder {
    String label;
    private DynamicListType dynamicListType;
    private ArrayList<MenuItem> items;

    private MenuBuilder() {
        items = new ArrayList<>();
    }

    public static Menu builTheMenu() {
        return null;
    }

    private MenuBuilder aMenu() {
        return new MenuBuilder();
    }

    private MenuBuilder aMenuExtending(Menu existingMenu) {
        MenuBuilder menuBuilder = new MenuBuilder();
        menuBuilder.label = existingMenu.getLabel();
        menuBuilder.items = new ArrayList<>(existingMenu.getItems());
        return menuBuilder;
    }

    private MenuBuilder withLabel(String label) {
        this.label = label;
        return this;
    }

    private MenuBuilder withItem(MenuItem item) {
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

    private MenuBuilder withDynamicList(DynamicListType dynamicListType) {
        this.dynamicListType = dynamicListType;
        return this;
    }

    private Menu build() {
        Menu menu = new Menu();
        menu.setLabel(label);
        menu.setItems(items);
        menu.setDynamicListType(dynamicListType);
        return menu;
    }

    private Menu buildMainMenu() {
        return aMenu()
                .withLabel("main menu")
                .withItem(new MenuItem(CommandType.NEW_GAME))
                .withItem(new MenuItem(CommandType.LOAD_GAME))
                .build();
    }

    private Menu buildVillageMenu() {
        return aMenu()
                .withLabel("village menu")
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(buildDynamicMenu("buildings", DynamicListType.BUILDINGS_LIST))
                .withItem(new MenuItem(CommandType.RESOURCES_INFO))
                .withItem(buildAttackMenu())
                .build();
    }

    private Menu buildAttackMenu() {
        return aMenu()
                .withLabel("attack")
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(new MenuItem(CommandType.LOAD_MAP))
                .withDynamicList(DynamicListType.AVAILABLE_MAPS_LIST)
                .build();
    }

    private Menu buildMapMenu() {
        return aMenu()
                .withLabel("map")
                .withItem(new MenuItem(CommandType.MAP_INFO))
                .withItem(new MenuItem(CommandType.ATTACK_MAP))
                .build();
    }

    private MenuItem buildDynamicMenu(String label, DynamicListType dynamicListType) {
        return aMenu()
                .withLabel(label)
                .withItem(new MenuItem(CommandType.BACK))
                .withDynamicList(dynamicListType)
                .build();
    }

    private Menu buildTypicalBuildingMenu() {
        return aMenu()
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(buildInfoMenu())
                .build();
    }

    private Menu buildInfoMenu() {
        return aMenu()
                .withLabel("info")
                .withItem(new MenuItem(CommandType.BACK))
                .withItem(new MenuItem(CommandType.OVERALL_INFO))
                .withItem(new MenuItem(CommandType.UPGRADE_INFO))
                .build();
    }

    private Menu buildTownHallMenu() {
        return aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("town hall")
                .withItem(buildDynamicMenu("available buildings", DynamicListType.AVAILABLE_BUILDINGS_LIST))
                .withItem(buildDynamicMenu("status", DynamicListType.CONSTRUCTION_STATUS_LIST))
                .build();
    }

    private Menu buildBarracksMenu() {
        return aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("barracks")
                .withItem(buildDynamicMenu("build soldiers", DynamicListType.TROOPS_LIST))
                .withItem(buildDynamicMenu("status", DynamicListType.TRAINING_STATUS_LIST))
                .build();
    }

    private Menu builCampMenu() {
        Menu infoMenu = aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.CAPACITY_INFO))
                .build();

        return aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("camp")
                .withItem(infoMenu)
                .withItem(buildDynamicMenu("soldiers", DynamicListType.AVAILABLE_TROOPS_LIST))
                .build();
    }

    private Menu buildMinesMenu() {
        return aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("mines")
                .withItem(buildDynamicMenu("mine", DynamicListType.MINE))
                .build();
    }

    private Menu buildStoragesMenu() {
        Menu infoMenu = aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.RESOURCES_INFO))
                .build();

        return aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("camp")
                .withItem(infoMenu)
                .build();
    }

    private Menu buildDefensiveTowersMenu() {
        Menu infoMenu = aMenuExtending(buildInfoMenu())
                .withItem(new MenuItem(CommandType.ATTACK_INFO))
                .build();

        return aMenuExtending(buildTypicalBuildingMenu())
                .withLabel("camp")
                .withItem(infoMenu)
                .withItem(buildDynamicMenu("target", DynamicListType.TARGET))
                .build();
    }
}
