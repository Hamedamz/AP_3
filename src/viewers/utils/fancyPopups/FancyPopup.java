package viewers.utils.fancyPopups;

import controllers.BuildingMenuController;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import models.GameLogic.Entities.Buildings.Building;
import viewers.utils.Const;
import viewers.utils.ImageLibrary;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

public abstract class FancyPopup extends Pane {

    Button closeButton = new RoundButton("X", "red");
    Label title = new Label();
    Pane content;

    Group root;
    Object model;
    AnimationTimer animationTimer;
    ImageView background;

    public FancyPopup(Object model) {
        this.model = model;
        this.title.setText(model.getClass().getSimpleName() + " " + ((Building) model).getID().getCount());
        this.setId("popup-view");
        setProperties();
    }

    public FancyPopup(String title) {
        this.title.setText(title);
        setProperties();
    }

    private void setProperties() {
        background = new ImageView(ImageLibrary.ZombieMenu.getImage());
        background.setFitWidth(Const.WINDOW_WIDTH);
        background.setFitHeight(Const.WINDOW_HEIGHT);

        this.title.setAlignment(Pos.CENTER);
        this.title.setMinWidth(Const.POPUP_WIDTH);
        this.title.setId("popup-title");

        closeButton.setLayoutY(210);
        closeButton.setLayoutX(300);
        closeButton.setOnAction(event -> {
            this.hide();
            SoundPlayer.play(Sounds.buttonSound);
        });

        this.setPrefSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);

        content = new StackPane();
        content.setPrefWidth(Const.POPUP_WIDTH);
        VBox wrapper = new VBox(Const.SPACING * 2, title, content);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setLayoutX(Const.WINDOW_WIDTH / 2 - Const.POPUP_WIDTH / 2);
        wrapper.setLayoutY(250);
        this.getChildren().addAll(background, closeButton, wrapper);

        addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case ESCAPE:
                    this.hide();
                    break;
            }
        });
    }

    public void setBody(GridPane body) {
        content.getChildren().add(body);
        body.setAlignment(Pos.CENTER);
    }

    public void show(Scene scene) {
        root = (Group) scene.getRoot();
        root.getChildren().add(this);
        if (animationTimer != null) {
            animationTimer.start();
        }
        BuildingMenuController.getInstance().toggleActiveMenu();
    }

    public void hide() {
        root.getChildren().remove(this);
        if (animationTimer != null) {
            animationTimer.stop();
        }
        BuildingMenuController.getInstance().toggleActiveMenu();
    }
}
