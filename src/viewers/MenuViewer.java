package viewers;

import controllers.enums.ModelBasedList;
import models.GameLogic.Entities.Buildings.TownHall;
import models.GameLogic.Entities.Entity;
import models.Menu.Menu;
import models.Menu.MenuItem;

public class MenuViewer extends BasicViewer {

    public static final String MENU_TITLE_FORMAT = "%s menu\n";
    public static final String MENU_ITEM_FORMAT = "%d. %s\n";

    public void printMenu(Menu menu, Entity relatedModel) {
        System.out.printf(MENU_TITLE_FORMAT, menu.getLabel());
        int index = 1;
        for (MenuItem menuItem : menu.getItems()) {
            if (menuItem.isCommand()) {
                System.out.printf(MENU_ITEM_FORMAT, index++, menuItem.getLabel());
            } else {
                index = printList(index, menuItem.getModelList(), relatedModel);
            }
        }
    }

    private int printList(int fromIndex, ModelBasedList modelList, Entity relatedModel) {
        switch (modelList) {
            case BUILDINGS_LIST:
                return printBuildingsList(fromIndex, (TownHall) relatedModel);
            case CONSTRUCTION_STATUS_LIST:
                return printConstructionStatusList(fromIndex, (TownHall) relatedModel);
            case AVAILABLE_TROOPS_LIST:
                return printAvailableTroopsList(fromIndex, (TownHall) relatedModel);
            case TRAINING_STATUS_LIST:
                return printTrainingStatusList(fromIndex, (TownHall) relatedModel);
            case AVAILABLE_MAPS_LIST:
                return printAvailableMapsList(fromIndex, (TownHall) relatedModel);
            case AVAILABLE_BUILDINGS_LIST:
                return printConstructableList(fromIndex, (TownHall) relatedModel);
            case RESOURCES_LIST:
                return printResourcesList(fromIndex, (TownHall) relatedModel);
            case TROOPS_LIST:
                return printTroopsList(fromIndex, (TownHall) relatedModel);
        }
        return fromIndex;
    }

    private int printTroopsList(int fromIndex, TownHall relatedModel) {
        // all troops with labels A and U that show availability
        // if troop can be built the possible number is also shown
        return 0;
    }

    private int printResourcesList(int fromIndex, TownHall relatedModel) {
        // resources
        return 0;
    }

    private int printConstructableList(int fromIndex, TownHall relatedModel) {
        // buildings than can be built based on resources and aren't built yet
        return 0;
    }

    private int printAvailableMapsList(int fromIndex, TownHall relatedModel) {
        // enemy maps that can be attacked
        return 0;
    }

    private int printTrainingStatusList(int fromIndex, TownHall relatedModel) {
        // troop that are being trained with turns left ro be trained
        return 0;
    }

    private int printAvailableTroopsList(int fromIndex, TownHall relatedModel) {
        // troops and number of each
        return 0;
    }

    private int printConstructionStatusList(int fromIndex, TownHall relatedModel) {
        // under construction buildings with turns left to be built
        return 0;
    }

    private int printBuildingsList(int fromIndex, TownHall relatedModel) {
        // all game buildings
        return fromIndex;
    }
}
