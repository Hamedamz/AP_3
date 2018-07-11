package viewers.utils;

import javafx.animation.FadeTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static viewers.utils.Const.TILE_SIZE;

public class PutTroopsTile extends Rectangle {
    int x;
    int y;

    public PutTroopsTile(int x, int y) {
        super(TILE_SIZE / 4, TILE_SIZE / 4);

        this.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(0.4);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), this);
        fadeOut.setFromValue(0.4);
        fadeOut.setToValue(0);

        this.setOnMouseEntered(event -> fadeIn.play());
        this.setOnMouseExited(event -> fadeOut.play());
    }
}
