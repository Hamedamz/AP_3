package viewers.utils.riverMenu;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import viewers.utils.ButtonActionType;
import viewers.utils.Const;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static viewers.utils.ButtonActionType.*;

public class RiverMenu extends Pane {
    private static RiverMenu instance = new RiverMenu();

    private List<RiverMenuPage> riverMenuPages;

    public static RiverMenu getInstance() {
        return instance;
    }

    // index 0
    private RiverButton playButton = new RiverButton(OPEN_PLAY_MENU, 0);
    private RiverButton optionsButton = new RiverButton(OPEN_OPTIONS_MENU, 0);
    private RiverButton exitButton = new RiverButton(EXIT, 0);
    private RiverMenuPage mainMenu = new RiverMenuPage(0, Const.RIVER_MENU_SIZE, playButton, optionsButton, exitButton);

    // index 1
    private RiverButton singlePlayerButton = new RiverButton(OPEN_SINGLE_PLAYER_MENU, 1);
    private RiverButton multiPlayerButton = new RiverButton(OPEN_MULTI_PLAYER_MENU, 1);
    private RiverMenuPage playChoices = new RiverMenuPage(1, Const.RIVER_MENU_SIZE * 2, singlePlayerButton, multiPlayerButton);
    private RiverMenuPage optionsMenu = new RiverMenuPage(1, Const.RIVER_MENU_SIZE * 2);

    // index 2
    private RiverButton newGameButton = new RiverButton(OPEN_NEW_GAME_MENU, 2);
    private RiverButton loadGameButton = new RiverButton(OPEN_LOAD_GAME_MENU, 2);
    private RiverMenuPage singlePlayerChoices = new RiverMenuPage(2, Const.RIVER_MENU_SIZE * 3, newGameButton, loadGameButton);
    private RiverButton hostButton = new RiverButton(OPEN_HOST_MENU, 2);
    private RiverButton clientButton = new RiverButton(OPEN_CLIENT_MENU, 2);
    private RiverMenuPage multiPlayerChoices = new RiverMenuPage(2, Const.RIVER_MENU_SIZE * 3, hostButton, clientButton);

    // index 3
    private RiverMenuPage newGameMenu = new RiverMenuPage(3, Const.RIVER_MENU_SIZE * 6, new Pane(new Circle(20)));
    private RiverMenuPage loadGameMenu = new RiverMenuPage(3, Const.RIVER_MENU_SIZE * 6, new Pane(new Circle(20)));
    private RiverMenuPage hostMenu = new RiverMenuPage(3, Const.RIVER_MENU_SIZE * 6, new Pane(new Circle(20)));
    private RiverMenuPage clientMenu = new RiverMenuPage(3, Const.RIVER_MENU_SIZE * 6, new Pane(new Circle(20)));

    public RiverMenu() {
        riverMenuPages = Arrays.asList(clientMenu, hostMenu, loadGameMenu, newGameMenu, singlePlayerChoices, multiPlayerChoices, optionsMenu, playChoices, mainMenu);
        this.getChildren().addAll(riverMenuPages);
        mainMenu.open();
    }

    public void openMenu(ButtonActionType type) {
        RiverMenuPage menuToOpen = null;
        switch (type) {
            case OPEN_PLAY_MENU:
                menuToOpen = playChoices;
                break;
            case OPEN_OPTIONS_MENU:
                menuToOpen = optionsMenu;
                break;
            case EXIT:
                break;
            case OPEN_SINGLE_PLAYER_MENU:
                menuToOpen = singlePlayerChoices;
                break;
            case OPEN_MULTI_PLAYER_MENU:
                menuToOpen = multiPlayerChoices;
                break;
            case OPEN_NEW_GAME_MENU:
                menuToOpen = newGameMenu;
                break;
            case OPEN_LOAD_GAME_MENU:
                menuToOpen = loadGameMenu;
                break;
            case OPEN_HOST_MENU:
                menuToOpen = hostMenu;
                break;
            case OPEN_CLIENT_MENU:
                menuToOpen = clientMenu;
                break;
        }

        if (menuToOpen == null) {
            return;
        }
        boolean isOpen = menuToOpen.isOpen();
        int menuIndex = menuToOpen.getIndex();
        for (RiverMenuPage riverMenuPage : riverMenuPages) {
            if (riverMenuPage.getIndex() >= menuIndex) {
                riverMenuPage.close();
            }
        }

        if (!isOpen) {
            menuToOpen.open();
        }
    }
}
