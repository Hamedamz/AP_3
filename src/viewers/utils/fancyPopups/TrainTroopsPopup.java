package viewers.utils.fancyPopups;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.TrainingTroop;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.TroopsScrollMenu;
import viewers.utils.ButtonActionType;

import java.util.ArrayList;

public class TrainTroopsPopup extends FancyPopup {

    private ScrollPane status;
    private VBox trainingTroopsList;
    private TroopsScrollMenu troopsScrollMenu;

    private TrainTroopsPopup(Object model) {
        super(model);
        setProperties();

        GridPane gridPane = new GridPane();
        gridPane.add(status, 0, 0);
        gridPane.add(troopsScrollMenu, 0, 1);
        gridPane.setVgap(Const.SPACING * 2);
        setBody(gridPane);
        setAnimationTimer(this);
    }

    public static void openPopup(Object model) {
        TrainTroopsPopup trainTroopsPopup = new TrainTroopsPopup(model);
        trainTroopsPopup.show(AppGUI.getMyVillageScene());
        trainTroopsPopup.requestFocus();
    }

    private void setProperties() {
        status = new ScrollPane();
        status.setId("scroll-menu");
        status.setMaxWidth(Const.POPUP_WIDTH - 3 * Const.SPACING);
        status.setPrefHeight(Const.POPUP_HEIGHT / 2);
        status.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        trainingTroopsList = new VBox(Const.SPACING);
        status.setContent(trainingTroopsList);
        troopsScrollMenu = new TroopsScrollMenu(ButtonActionType.TROOPS, model);
        troopsScrollMenu.buildForTraining();
        troopsScrollMenu.setMaxWidth(Const.POPUP_WIDTH - 6 * Const.SPACING);
    }

    private void refresh() {
        troopsScrollMenu.refreshForTraining();

        Barracks barracks = (Barracks) model;
        ArrayList<TrainingTroop> trainingTroops = barracks.getTrainingTroops();
        trainingTroopsList.getChildren().clear();
        for (TrainingTroop trainingTroop : trainingTroops) {
            Label label = new Label(String.format("%s %ds", trainingTroop.getTroop().getClass().getSimpleName(), trainingTroop.getTimeRemaining()));
            label.setTextFill(Color.WHITE);
            trainingTroopsList.getChildren().add(label);
        }
    }

    private void setAnimationTimer(TrainTroopsPopup popup) {
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                popup.refresh();
            }
        };
    }
}
