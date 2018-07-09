package viewers.utils;

import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;
import viewers.AppGUI;

public class ErrorPopup {
    private static int showing = 0;

    public static void popError(Exception e) {
        Text text = new Text(e.getMessage());
        text.setFill(Color.RED);
        text.setId("error-text");
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
