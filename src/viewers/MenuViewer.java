package viewers;

import controllers.enums.ModelBasedList;
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
                printList(menuItem.getModelList(), relatedModel);
            }
        }
    }

    private void printList(ModelBasedList modelList, Entity relatedModel) {

    }
}
