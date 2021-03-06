package viewers.oldViewers;

import viewers.menu.DynamicMenuItem;
import viewers.menu.Menu;
import viewers.menu.MenuItem;

import java.util.*;

public class MenuViewer extends BasicViewer {

    public static final String MENU_TITLE_FORMAT = "%s menu :\n";
    public static final String MENU_ITEM_FORMAT = "%d. %s\n";
    public static final String MENU_ITEM_INFO_FORMAT = "%d. %s %s\n";

    public void printMenu(Menu menu) {
        System.out.printf(MENU_TITLE_FORMAT, menu.getLabel());
        printMenuItemsList(menu.getItems(), menu.getDynamicItemsHashMap());
    }

    private void printMenuItemsList(ArrayList<MenuItem> Items, LinkedHashMap<DynamicMenuItem, String> dynamicItems) {
        int index = 1;
        for (MenuItem item : Items) {
            System.out.printf(MENU_ITEM_FORMAT, index++, item.getLabel());
        }

        for (Map.Entry<DynamicMenuItem, String> dynamicItem : dynamicItems.entrySet()) {
            System.out.printf(MENU_ITEM_INFO_FORMAT, index++, dynamicItem.getKey().getLabel(), dynamicItem.getValue());
        }
    }
}
