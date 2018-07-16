package viewers.utils;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import viewers.utils.fancyButtons.RoundButton;

public class VillageLockPane extends StackPane {
    private RoundButton watchButton;

    public VillageLockPane() {
        this.setMinSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        this.setId("glass-pane");
        StrokeText strokeText = new StrokeText("Your Village is Being Raided By Enemy!");
        watchButton = new RoundButton("Watch!", "yellow");
        VBox vBox = new VBox(Const.SPACING * 3, strokeText, watchButton);
        vBox.setMinWidth(Const.WINDOW_WIDTH);
        vBox.setAlignment(Pos.CENTER);

        this.getChildren().add(vBox);
    }
}
