package viewers;

import controllers.enums.ModelBasedList;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Resource;
import models.GameLogic.Village;
import models.Menu.Menu;
import models.Menu.MenuItem;

public class MenuViewer extends BasicViewer {

    public static final String MENU_TITLE_FORMAT = "%s menu\n";
    public static final String MENU_ITEM_FORMAT = "%d. %s\n";

    private Village village;

    public MenuViewer(Village village) {
        this.village = village;
    }

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
                return printBuildingsList(fromIndex);
            case CONSTRUCTION_STATUS_LIST:
                return printConstructionStatusList(fromIndex);
            case AVAILABLE_TROOPS_LIST:
                return printAvailableTroopsList(fromIndex);
            case TRAINING_STATUS_LIST:
                return printTrainingStatusList(fromIndex);
            case AVAILABLE_MAPS_LIST:
                return printAvailableMapsList(fromIndex);
            case AVAILABLE_BUILDINGS_LIST:
                return printConstructableList(fromIndex);
            case RESOURCES_LIST:
                return printResourcesList(fromIndex);
            case TROOPS_LIST:
                return printTroopsList(fromIndex);
        }
        return fromIndex;
    }

    private int printTroopsList(int fromIndex) {
        // all troops with labels A and U that show availability
        // if troop can be built the possible number is also shown
        return 0;
    }

    private int printResourcesList(int fromIndex) {
        // resources
        Resource totalResourceStock = village.getTotalResourceStock();
        System.out.println("Gold: " + totalResourceStock.getGold());
        System.out.println("Elixir: " + totalResourceStock.getElixir());
        System.out.println("Score: " + village.getTownHall().getScore());
        return 0;
    }

    private int printConstructableList(int fromIndex) {
        // buildings than can be built based on resources and aren't built yet
        return 0;
    }

    private int printAvailableMapsList(int fromIndex) {
        // enemy maps that can be attacked
        return 0;
    }

    private int printTrainingStatusList(int fromIndex) {
        // troop that are being trained with turns left ro be trained
        return 0;
    }

    private int printAvailableTroopsList(int fromIndex) {
        // troops and number of each
        return 0;
    }

    private int printConstructionStatusList(int fromIndex) {
        // under construction buildings with turns left to be built
        return 0;
    }

    private int printBuildingsList(int fromIndex) {
        // all game buildings
        return fromIndex;
    }
}
