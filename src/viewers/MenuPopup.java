package viewers;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import viewers.menu.Menu;

public class MenuPopup {
    private static Stage mainStage;
    private Popup popup;

    private MenuPopup(){
    }

    /**
     *
     * @param label: name of the class of the building
     * @param info: information that should be shown.
     */
    public static void popupMenu(String label, String info) {
        MenuPopup menuPopup = new MenuPopup();
        menuPopup.popup = new Popup();
        menuPopup.popup.setX(400); // TODO: 6/29/2018 read numbers from constants
        menuPopup.popup.setY(200);
        VBox vBox = new VBox();
        GridPane upperPane = new GridPane();
        GridPane infoPane = new GridPane();
        Button exit = new Button("X");
        Label popupLabel = new Label(label);
        Rectangle labelRectangle = new Rectangle(200, 200, 480, 25);
        Rectangle contentRectangle = new Rectangle(200, 200, 480, 320);
        ImageView imageView = new ImageView(); // TODO: 6/29/2018 Load image according to label
        Label popupInfo = new Label(info);
        contentRectangle.setArcWidth(15);
        contentRectangle.setArcHeight(15);
        contentRectangle.setFill(Color.WHITE);
        exit.setOnAction(event -> menuPopup.popup.hide());
        GridPane.setHalignment(exit, HPos.RIGHT);
        labelRectangle.setFill(Color.LAWNGREEN);
        labelRectangle.setArcHeight(15);
        labelRectangle.setArcWidth(15);
        popupLabel.setFont(Font.loadFont(AppGUI.class.getResource("/viewers/styles/font.ttf").toExternalForm(), 10));
        popupInfo.setFont(Font.loadFont(AppGUI.class.getResource("/viewers/styles/font.ttf").toExternalForm(), 10));
        GridPane.setHalignment(popupLabel, HPos.CENTER);
        upperPane.getChildren().addAll(labelRectangle, exit, popupLabel);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(imageView, popupInfo);
        infoPane.getChildren().addAll(contentRectangle, hBox);
        GridPane.setHalignment(imageView, HPos.LEFT);
        exit.getStylesheets().add("/viewers/styles/exitButton.css");
        vBox.getChildren().addAll(upperPane, infoPane);
        menuPopup.popup.getContent().addAll(vBox);
        menuPopup.popup.show(mainStage);
    }

    public static void setStage(Stage stage) {
        mainStage = stage;
    }
}
