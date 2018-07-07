package viewers.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import models.GameLogic.Entities.Entity;

public class InfoPopup extends Popup{

    private Entity entity;
    private String title;
    private ImageView imageView;
    private VBox progressBarContainer;
    private VBox propertyInfoItemContainer;

    public InfoPopup(Entity entity) {
        super();
        this.entity = entity;

        progressBarContainer = new VBox(Const.SPACING);
        propertyInfoItemContainer = new VBox(Const.SPACING);
        HBox content = new HBox(Const.SPACING * 2, imageView, new VBox(Const.SPACING * 2, progressBarContainer, propertyInfoItemContainer));
        this.getContent().add(content);
    }

    public InfoPopup withImage(Image image) {
        this.imageView.setImage(image);
        return this;
    }

    public InfoPopup withProgressBarItem(ProgressBarItem progressBarItem) {
        progressBarContainer.getChildren().add(progressBarItem);
        return this;
    }

    public InfoPopup withPropertyInfoItem(PropertyInfoItem propertyInfoItem) {
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
