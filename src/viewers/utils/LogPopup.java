package viewers.utils;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;
import viewers.AppGUI;

public class LogPopup {
    private static int showing = 0;

    public static void popError(Exception e) {
        Label text = new Label(e.getMessage());
        text.setTextFill(Color.RED);
        Popup popup = new Popup();
        popup.getContent().add(text);
        text.setLayoutY(showing * 30);
        popup.show(AppGUI.getMainStage());
        showing++;
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), text);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setDelay(Duration.millis(2000));
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            popup.hide();
            showing--;
        });
    }

    public static void popInfo(String info) {
        Label text = new Label(info);
        text.setTextFill(Color.DODGERBLUE);
        Popup popup = new Popup();
        popup.getContent().add(text);
        text.setLayoutY(showing * 30);
        popup.show(AppGUI.getMainStage());
        showing++;
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), text);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setDelay(Duration.millis(2000));
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            popup.hide();
            showing--;
        });
    }
}
