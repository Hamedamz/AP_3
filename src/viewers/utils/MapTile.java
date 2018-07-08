package viewers.utils;

import javafx.scene.shape.Rectangle;

public class MapTile extends Rectangle {

    private int mapX;
    private int mapY;

    public MapTile(double width, double height, int x, int y) {
        super(width, height);
        this.mapX = x;
        this.mapY = y;
        this.setOpacity(0);
        this.setOnMouseEntered(event -> this.setOpacity(0.25));
        this.setOnMouseExited(event -> this.setOpacity(0));
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }
}
