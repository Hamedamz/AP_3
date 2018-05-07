package viewers;

import models.GameLogic.Resource;
import models.GameLogic.Village;
import models.Menu.DynamicMenuItem;
import models.Menu.Menu;
import models.Menu.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuViewer extends BasicViewer {

    public static final String MENU_TITLE_FORMAT = "%s menu :\n";
    public static final String MENU_ITEM_FORMAT = "%d. %s\n";
    public static final String MENU_ITEM_INFO_FORMAT = "%d. %s %s\n";

    public void printMenu(Menu menu) {
        System.out.printf(MENU_TITLE_FORMAT, menu.getLabel());
        printMenuItemsList(menu.getItems(), menu.getDynamicItemsHashMap());
    }

    private void printMenuItemsList(ArrayList<MenuItem> Items, HashMap<DynamicMenuItem, String> dynamicItems) {
        int index = 1;
        for (MenuItem item : Items) {
            System.out.printf(MENU_ITEM_FORMAT, index++, item.getLabel());
        }
        for (Map.Entry<DynamicMenuItem, String> dynamicItem : dynamicItems.entrySet()) {
            System.out.printf(MENU_ITEM_INFO_FORMAT, index++, dynamicItem.getKey().getLabel(), dynamicItem.getValue());
        }
    }
}
