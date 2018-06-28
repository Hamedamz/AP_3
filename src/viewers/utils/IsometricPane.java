package viewers.utils;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import static viewers.utils.Const.*;

public class IsometricPane extends Pane {

    public IsometricPane(Pane pane) {
        super();
        pane.setRotationAxis(new Point3D(0,0,1));
        pane.setRotate(45);
        Pane wrapperPane = new Pane();
        wrapperPane.setRotationAxis(new Point3D(1,0,0));
        wrapperPane.setRotate(GRID_PANE_ROTATE_Y);
        wrapperPane.getChildren().add(pane);
        wrapperPane.setLayoutX(GRID_PANE_LAYOUT_X);
        wrapperPane.setLayoutY(GRID_PANE_LAYOUT_Y);
        this.getChildren().add(wrapperPane);
    }
}
