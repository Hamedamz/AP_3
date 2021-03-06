package viewers;

import controllers.BuildingMenuController;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.TownHall;
import models.GameLogic.GameEngine;
import viewers.utils.*;
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.ButtonActionType;
import viewers.utils.fancyButtons.RoundFancyButton;

import java.util.ArrayList;

import static viewers.utils.Const.*;

public abstract class VillageScene extends Scene {

    Group root;
    RoundFancyButton settingsButton;
    RoundFancyButton fastForwardButton;
    Pane buildingsPane;
    ArrayList<BuildingHolder> buildingHolders = new ArrayList<>();
    MapBrowserPane draggableView;
    ImageView villageBackground = new ImageView();
    private boolean pause = false;

    VillageConsole villageConsole;

    public VillageScene() {
        super(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        root = (Group) getRoot();
        this.getStylesheets().add("/viewers/styles/game.css");
        build();
    }

    public void build() {
        //graphical structure
        villageBackground.setImage(ImageLibrary.VillageBackground.getImage());

        draggableView = new MapBrowserPane(villageBackground);
        draggableView.setMaxWidth(VIllAGE_BACKGROUND_WIDTH);
        draggableView.setMaxHeight(VIllAGE_BACKGROUND_HEIGHT);
        draggableView.initialize();

        settingsButton = new RoundFancyButton(ButtonActionType.SETTINGS, "red");
        settingsButton.setLayoutX(Const.WINDOW_WIDTH - 85);
        settingsButton.setLayoutY(Const.WINDOW_HEIGHT - 200);
        settingsButton.setOnMouseClicked(event -> {
            BuildingMenuController.getInstance().hideActiveMenu();
            AppGUI.getController().getSoundPlayer().play(Sounds.buttonSound);
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(settingsButton.getIcon());
            rotateTransition.setDuration(Duration.millis(1000));
            rotateTransition.setFromAngle(0);
            rotateTransition.setToAngle(360);
            rotateTransition.play();
        });

        fastForwardButton = new RoundFancyButton(ButtonActionType.FAST_FORWARD, "red");
        fastForwardButton.setLayoutX(Const.WINDOW_WIDTH - 85);
        fastForwardButton.setLayoutY(Const.SPACING);
        fastForwardButton.setOnMouseClicked(event -> {
            GameEngine gameEngine = AppGUI.getController().getWorld().getGameEngine();
            if (gameEngine.getDuration() - 10 > 0) {
                gameEngine.changeDuration(gameEngine.getDuration() - 10);
                fastForwardButton.getLabel().setText("Hurry " + String.valueOf((40 - gameEngine.getDuration() + 10) / 10));
            } else {
                gameEngine.changeDuration(gameEngine.DEFAULT_DURATION);
                fastForwardButton.getLabel().setText("Hurry " + String.valueOf((40 - gameEngine.DEFAULT_DURATION) / 10));
            }
        });

        //village console
        villageConsole = new VillageConsole();
        villageConsole.setVillage(AppGUI.getController().getWorld().getMyVillage());

        //handling total village keyEvents
        addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case BACK_QUOTE:
                    if (villageConsole.isMinimized()) {
                        villageConsole.maximize();
                    } else {
                        villageConsole.minimize();
                    }
                    break;
                case S:
                    if (keyEvent.isControlDown()) {
                        AppGUI.getController().saveGame();
                        LogPopup.popInfo("Game Saved!");
                    }
                case P:
                    if (pause) {
                        AppGUI.getController().getWorld().getGameEngine().changeDuration(GameEngine.DEFAULT_DURATION);
                        pause = false;
                    } else {
                        AppGUI.getController().getWorld().getGameEngine().changeDuration(100000);
                        pause = true;
                    }
            }
        });
    }

    protected abstract AnimationTimer setAnimationTimer();

    public void addBuildingsFromList(ArrayList<Building> buildings) {
        buildings.sort((o1, o2) -> o2.getPosition().getMapX() - o1.getPosition().getMapX() + o2.getPosition().getMapY() - o1.getPosition().getMapY());
        for (Building building : buildings) {
            addBuildingToScene(new BuildingHolder(building));
        }
    }

    public void addBuildingToScene(BuildingHolder buildingHolder) {
        int size = (buildingHolder.getEntity().getClass().equals(TownHall.class)) ? 2 : 1;
        IsometricPane.mapToIsometricLayout(buildingHolder, buildingHolder.getEntity().getPosition(), size);
        buildingsPane.getChildren().add(buildingHolder);
        buildingHolders.add(buildingHolder);
    }

    public void toggleVisibility(Node node) {
        if (node.isVisible()) {
            node.setVisible(false);
        } else {
            node.setVisible(true);
        }
    }

    public void handleException(Exception e) {
        LogPopup.popError(e);
    }
}
