package viewers.utils;

import javafx.scene.shape.Rectangle;

public class MapTile extends Rectangle {
    public MapTile(double width, double height) {
        super(width, height);
        this.setId("map-tile");
//        this.setOpacity(0);
//        this.setOnMouseMoved(event -> this.setOpacity(0.25));
    }
}
