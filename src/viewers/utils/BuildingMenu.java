package viewers.utils;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import models.GameLogic.Entities.Buildings.Building;
import viewers.AppGUI;
import viewers.utils.fancyButtons.BuildingMenuFancyButton;
import viewers.utils.fancyButtons.RoundButton;

public class BuildingMenu extends StackPane {

    private RoundButton closeButton;
    private Label title;
    private Building building;
    private GridPane content;
    private HBox buttonsContainer = new HBox(Const.SPACING);

    public BuildingMenu(ButtonActionType... actions) {
        closeButton = new RoundButton("X", "red");
        closeButton.setOnAction(event -> hide());

        title = new Label();
        title.setAlignment(Pos.CENTER);
        title.setTextFill(Color.WHITE);
        title.setId("stroke-text");

        content = new GridPane();
        content.setHgap(Const.SPACING);
        content.setVgap(Const.SPACING);
        content.setAlignment(Pos.CENTER);
        for (int i = 0; i < actions.length; i++) {
            content.add(new BuildingMenuFancyButton(actions[i]), i, 1);
            buttonsContainer.getChildren().add(new BuildingMenuFancyButton(actions[i]));
        }
        content.add(new StackPane(title), 0, 0, actions.length, 1);
        this.setPrefWidth(Const.WINDOW_WIDTH - 200);
        this.setLayoutX(100);
        this.setLayoutY(Const.WINDOW_HEIGHT - 150);
        this.getChildren().add(content);
        this.hide();
    }

    public void toggle() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void show() {
        if (!isVisible()) {
            setVisible(true);
        }
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void hide() {
        if (isVisible()) {
            this.setVisible(false);
        }
    }
}
