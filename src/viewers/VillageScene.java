package viewers;

import controllers.BuildingMenuController;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Exceptions.NoSuchAUnderConstructBuildingException;
import models.GameLogic.GameEngine;
import models.GameLogic.Position;
import viewers.utils.*;
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.fancyButtons.ButtonActionType;
import viewers.utils.fancyButtons.RoundFancyButton;

import java.util.ArrayList;

import static viewers.utils.Const.*;

public abstract class VillageScene extends Scene {

    Group root;
    RoundFancyButton settingsButton;
    RoundFancyButton fastForwardButton;
    ArrayList<BuildingHolder> buildingHolders;
    MapBrowserPane draggableView;
    ImageView villageBackground = new ImageView();

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
        settingsButton.setLayoutX(Const.WINDOW_WIDTH - 100);
        settingsButton.setLayoutY(Const.WINDOW_HEIGHT - 200);
        settingsButton.setOnMouseClicked(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(settingsButton.getIcon());
            rotateTransition.setDuration(Duration.millis(1000));
            rotateTransition.setFromAngle(0);
            rotateTransition.setToAngle(360);
            rotateTransition.play();
        });

//        fastForwardButton = new RoundFancyButton(ButtonActionType.FAST_FORWARD, "red");
//        fastForwardButton.setLayoutX(Const.WINDOW_WIDTH - 100);
//        fastForwardButton.setLayoutY(Const.SPACING);
//        fastForwardButton.setOnMousePressed(event -> {
//            GameEngine gameEngine = AppGUI.getController().getSinglePlayerWorld().getGameEngine();
//            if (gameEngine.getDuration() - 10 > 0)
//            gameEngine.changeDuration(gameEngine.getDuration() - 10);
//        });
//        fastForwardButton.setOnMouseReleased(event -> {
//            GameEngine gameEngine = AppGUI.getController().getSinglePlayerWorld().getGameEngine();
//            gameEngine.changeDuration(GameEngine.DEFAULT_DURATION);
//            System.out.println("released");
//        });

        //village console
        villageConsole = new VillageConsole();
        villageConsole.setVillage(AppGUI.getController().getSinglePlayerWorld().getMyVillage());

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
            }
        });
    }

    abstract AnimationTimer setAnimationTimer();

    public void toggleVisibility(Node node) {
        if (node.isVisible()) {
            node.setVisible(false);
        } else {
            node.setVisible(true);
        }
    }

    public void handleException(Exception e) {
        ErrorPopup.popError(e);
    }
}
