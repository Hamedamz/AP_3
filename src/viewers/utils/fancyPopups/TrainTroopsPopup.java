package viewers.utils.fancyPopups;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import models.GameLogic.Entities.Buildings.Barracks;
import models.GameLogic.TrainingTroop;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.TroopsScrollMenu;
import viewers.utils.fancyButtons.ButtonActionType;

import java.util.ArrayList;

public class TrainTroopsPopup extends FancyPopup {

    private ScrollPane status;
    private VBox trainingTroopsList;
    private TroopsScrollMenu troopsScrollMenu;
    private AnimationTimer animationTimer;

    private TrainTroopsPopup(Object model) {
        super(model);
        setProperties();
        VBox body = new VBox(Const.SPACING * 2, status, troopsScrollMenu);
        body.setAlignment(Pos.CENTER);
        setBody(body);
        setAnimationTimer(this);

        this.setOnShown(event -> animationTimer.start());

        this.setOnHidden(event -> animationTimer.stop());
    }

    public static void openPopup(Object model) {
        new TrainTroopsPopup(model).show(AppGUI.getMainStage());
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
            trainingTroopsList.getChildren().add(new Label(String.format("%s %ds",trainingTroop.getTroop().getClass().getSimpleName(), trainingTroop.getTimeRemaining())));
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
