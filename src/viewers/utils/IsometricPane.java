package viewers.utils;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import models.GameLogic.Position;
import viewers.utils.entityHolders.TroopsHolder;

import static viewers.utils.Const.*;

public class IsometricPane extends Pane {

    public IsometricPane(Pane pane) {
        super();
        pane.setRotationAxis(new Point3D(0,0,1));
        pane.setRotate(45);
        Pane wrapperPane = new Pane(pane);
        wrapperPane.getTransforms().add(new Scale(1, GRID_PANE_SCALE_Y));
        wrapperPane.setLayoutX(GRID_PANE_LAYOUT_X);
        wrapperPane.setLayoutY(GRID_PANE_LAYOUT_Y);
        this.getChildren().add(wrapperPane);
    }

    public static void mapToIsometricLayout(Node node, Position position, int size) {
        node.setLayoutX(MAP_ORIGIN_X - ISOMETRIC_TILE_WIDTH / 2 * size + (position.getX() - position.getY()) * ISOMETRIC_TILE_SIZE / 4 * MAP_SLOPE_TRANSFORM_X);
        node.setLayoutY(MAP_ORIGIN_Y + (position.getX() + position.getY()) * ISOMETRIC_TILE_SIZE / 4 * MAP_SLOPE_TRANSFORM_Y);
    }
}
