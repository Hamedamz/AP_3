package viewers.utils;

import controllers.BuildingMenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import models.GameLogic.Entities.Buildings.TownHall;
import models.GameLogic.Entities.Entity;
import viewers.AppGUI;

public class InfoPopup extends Popup {

    private Object model;
    private Label title = new Label();
    private Button closeButton = new Button("X");
    private ImageView imageView = new ImageView();
    private VBox imagePane = new VBox(imageView);
    private VBox progressBarContainer = new VBox(Const.SPACING);
    private VBox propertyInfoItemContainer = new VBox(Const.SPACING);
    private VBox content = new VBox(Const.SPACING * 2);
    private Pane wrapper = new Pane(content, closeButton);

    private InfoPopup(Object model) {
        this.model = model;
        this.title.setText(model.getClass().getSimpleName());
        content.getChildren().addAll(title, new HBox(imagePane, new VBox(Const.SPACING * 2, progressBarContainer, propertyInfoItemContainer)));
        this.getContent().add(wrapper);
        setProperties();
    }

    private void setProperties() {
        this.title.setAlignment(Pos.CENTER);
        this.title.setMinWidth(Const.POPUP_WIDTH);
        this.title.setId("popup-title");

        imagePane.setMinWidth(Const.POPUP_WIDTH / 2);
        imagePane.setAlignment(Pos.CENTER);
        if (model.getClass().equals(TownHall.class)) {
            imageView.setFitWidth(Const.TOWNHALL_TILE_WIDTH / 2);
            imageView.setFitHeight(Const.TOWNHALL_TILE_HEIGHT / 2);
        }

        content.setPadding(new Insets(10));

        wrapper.setId("popup-wrapper");
        wrapper.setMinHeight(Const.POPUP_HEIGHT);

        closeButton.setId("close-button");
        closeButton.setOnAction(event -> {
            this.hide();
            SoundPlayer.play(Sounds.buttonSound);
        });
        closeButton.setLayoutY(18);
        closeButton.setLayoutX(18);

        this.setOnShowing(event -> BuildingMenuController.getInstance().toggleActiveMenu());
        this.setOnHiding(event -> BuildingMenuController.getInstance().toggleActiveMenu());
    }

    public static void openPopup(Object model, InfoPopupItems items) {

        InfoPopup infoPopup = new InfoPopup(model).withImage(((Entity) model).getImageView());

        for (ProgressBarType progressBarType : items.getProgressBarTypes()) {
            infoPopup.withProgressBarItem(new ProgressBarItem(progressBarType, model));
        }

        for (PropertyInfoType propertyInfoType : items.getPropertyInfoTypes()) {
            infoPopup.withPropertyInfoItem(new PropertyInfoItem(propertyInfoType, model));
        }
        infoPopup.show(AppGUI.getMainStage());
    }


    public InfoPopup withImage(ImageView imageView) {
        this.imageView.setImage(imageView.getImage());
        this.imageView.setViewport(imageView.getViewport());
        return this;
    }

    private InfoPopup withProgressBarItem(ProgressBarItem progressBarItem) {
        progressBarContainer.getChildren().add(progressBarItem);
        return this;
    }

    private InfoPopup withPropertyInfoItem(PropertyInfoItem propertyInfoItem) {
        propertyInfoItemContainer.getChildren().add(propertyInfoItem);
        return this;
    }

    //    public static void popupMenu(String label, String info) {
//        infoPopup menuPopup = new infoPopup();
//        menuPopup.popup = new Popup();
//        menuPopup.popup.setX(400); // TODO: 6/29/2018 read numbers from constants
//        menuPopup.popup.setY(200);
//        VBox vBox = new VBox();
//        GridPane upperPane = new GridPane();
//        GridPane infoPane = new GridPane();
//        Button exit = new Button("X");
//        Label popupLabel = new Label(label);
//        Rectangle labelRectangle = new Rectangle(200, 200, 480, 25);
//        Rectangle contentRectangle = new Rectangle(200, 200, 480, 320);
//        ImageView imageView = new ImageView(); // TODO: 6/29/2018 Load image according to label
//        Label popupInfo = new Label(info);
//        contentRectangle.setArcWidth(15);
//        contentRectangle.setArcHeight(15);
//        contentRectangle.setFill(Color.WHITE);
//        exit.setOnAction(event -> menuPopup.popup.hide());
//        GridPane.setHalignment(exit, HPos.RIGHT);
//        labelRectangle.setFill(Color.LAWNGREEN);
//        labelRectangle.setArcHeight(15);
//        labelRectangle.setArcWidth(15);
//        popupLabel.setFont(Font.loadFont(AppGUI.class.getResource("/viewers/styles/font.ttf").toExternalForm(), 10));
//        popupInfo.setFont(Font.loadFont(AppGUI.class.getResource("/viewers/styles/font.ttf").toExternalForm(), 10));
//        GridPane.setHalignment(popupLabel, HPos.CENTER);
//        upperPane.getChildren().addAll(labelRectangle, exit, popupLabel);
//        HBox hBox = new HBox();
//        hBox.getChildren().addAll(imageView, popupInfo);
//        infoPane.getChildren().addAll(contentRectangle, hBox);
//        GridPane.setHalignment(imageView, HPos.LEFT);
//        exit.getStylesheets().add("/viewers/styles/exitButton.css");
//        vBox.getChildren().addAll(upperPane, infoPane);
//        menuPopup.popup.getContent().addAll(vBox);
//        menuPopup.popup.show(mainStage);
//    }
//
//    public static void setStage(Stage stage) {
//        mainStage = stage;
//    }
}
