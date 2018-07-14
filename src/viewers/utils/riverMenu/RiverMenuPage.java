package viewers.utils.riverMenu;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import viewers.utils.Const;

public class RiverMenuPage extends Pane {

    private boolean isOpen;
    private int index;
    private double width;
    private VBox buttons;
    private Pane content;

    public RiverMenuPage(int index, double width, Pane content) {
        this.index = index;
        this.width = width;

        this.content = content;
        this.content.setLayoutX(width - Const.RIVER_MENU_SIZE / 2);
        this.getChildren().add(this.content);
        setProperties();
    }


    public RiverMenuPage(int index, double width, Button... buttons) {
        this.index = index;
        this.width = width;

        this.buttons = new VBox(Const.SPACING);
        this.buttons.setPadding(new Insets(Const.SPACING));
        this.buttons.getChildren().addAll(buttons);
        this.buttons.setAlignment(Pos.CENTER);
        this.buttons.setLayoutX(width - Const.RIVER_MENU_SIZE - Const.SPACING);
        this.getChildren().add(this.buttons);
        setProperties();
    }

    private void setProperties() {
        this.setMaxSize(width, Const.WINDOW_HEIGHT);
        this.setPrefWidth(width);
        this.isOpen = false;
        this.setLayoutX(- width);
    }

    public void open() {
        if (!isOpen) {
            isOpen = true;
            animate(0);
        }
    }

    public void close() {
        if (isOpen) {
            isOpen = false;
            animate(- width);
        }
    }

    private void animate(double x) {
        Timeline timeline = new Timeline();
        KeyValue keyValue;
        keyValue = new KeyValue(this.layoutXProperty(), x);
        KeyFrame keyFrame = new KeyFrame(Const.TRANSITION_DURATION, keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public int getIndex() {
        return index;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
