package viewers.utils;

import javafx.animation.FadeTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import viewers.AppGUI;

public class MapTile extends Rectangle {

    private int mapX;
    private int mapY;

    public MapTile(double width, double height, int x, int y) {
        super(width, height);
        this.mapX = x;
        this.mapY = y;

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(0.2);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), this);
        fadeOut.setFromValue(0.2);
        fadeOut.setToValue(0);

        this.setOpacity(0);
        this.setOnMouseEntered(event -> fadeIn.play());
        this.setOnMouseExited(event -> fadeOut.play());
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }
}
