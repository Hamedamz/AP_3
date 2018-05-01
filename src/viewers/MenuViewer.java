package viewers;

import controllers.Exceptions.InvalidInputException;
import controllers.enums.DynamicListType;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Resource;
import models.GameLogic.Village;
import models.Menu.Menu;
import models.Menu.MenuItem;

import java.util.ArrayList;

public class MenuViewer extends BasicViewer {

    public static final String MENU_TITLE_FORMAT = "%s menu\n";
    public static final String MENU_ITEM_FORMAT = "%d. %s\n";

    private Village village;

    public MenuViewer(Village village) {
        this.village = village;
    }

    public void printMenu(Menu menu, Entity... relatedModel) {
        System.out.printf(MENU_TITLE_FORMAT, menu.getLabel());
        printMenuItemsList(menu.getItems(), menu.getDynamicItems());
    }

    private void printMenuItemsList(ArrayList<MenuItem>... lists) {
        int index = 1;
        for (ArrayList<MenuItem> list : lists) {
            for (MenuItem menuItem : list) {
                System.out.printf(MENU_ITEM_FORMAT, index++, menuItem.getLabel());
            }
        }
    }

    private int printResourcesList() {
        Resource totalResourceStock = village.getTotalResourceStock();
        System.out.println("Gold: " + totalResourceStock.getGold());
        System.out.println("Elixir: " + totalResourceStock.getElixir());
        System.out.println("Score: " + village.getTownHall().getScore());
        return 0;
    }

    public int getMenuItemIndex() throws InvalidInputException {
        String itemNumber = getInput();
        if (!itemNumber.matches("\\d+")) {
            throw new InvalidInputException("Input is not valid, please enter a number.");
        }
        return Integer.parseInt(itemNumber) - 1;
    }
}
