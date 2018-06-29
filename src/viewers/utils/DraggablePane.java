package viewers.utils;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import static viewers.utils.Const.*;

public class DraggablePane extends Pane {
    private double startX;
    private double startY;

    public DraggablePane(Node... children) {
        super(children);
    }

    public void initialize() {
        this.setOnMousePressed(event -> {
            this.setCursor(Cursor.CLOSED_HAND);
            startX = event.getSceneX() - this.getLayoutX();
            startY = event.getSceneY() - this.getLayoutY();
        });

        this.setOnMouseReleased(event -> this.setCursor(Cursor.DEFAULT));

        this.setOnMouseDragged(event -> {
            if (isLayoutXValid(event.getSceneX() - startX)) {
                this.setLayoutX(event.getSceneX() - startX);
            }
            if (isLayoutYValid(event.getSceneY() - startY)) {
                this.setLayoutY(event.getSceneY() - startY);
            }
        });

        this.setOnScroll(event -> {
            if (isScaleYValid(event.getDeltaY() / 100 + this.getScaleY()) && isScaleXValid(event.getDeltaY() / 100 + this.getScaleX())) {
//                new ZoomOperator().zoom(this, event.getDeltaY() / 100 + this.getScaleX(), event.getSceneX(), event.getSceneY());
                this.setScaleX(event.getDeltaY() / 100 + this.getScaleX());
                this.setScaleY(event.getDeltaY() / 100 + this.getScaleY());
            }
        });

    }

    private boolean isScaleXValid(double scale) {
        return scale > 0 && this.getMaxWidth() * scale >= WINDOW_WIDTH;
    }

    private boolean isScaleYValid(double scale) {
        return scale > 0 && this.getMaxHeight() * scale >= WINDOW_HEIGHT;
    }

    private boolean isLayoutXValid(double layoutX) {
        return layoutX >= WINDOW_WIDTH - this.getMaxWidth() && layoutX <= 0;
    }

    private boolean isLayoutYValid(double layoutY) {
        return layoutY >= WINDOW_HEIGHT - this.getMaxHeight() && layoutY <= 0;
    }
}

class ZoomOperator {

    public ZoomOperator() {

    }

    public void zoom(Node node, double scale, double x, double y) {
        // determine scale

        double dx = ((node.getLayoutX() - x) / node.getScaleX()) * scale;
        double dy = ((node.getLayoutY() - y) / node.getScaleY()) * scale;

        node.setLayoutX(x + dx);
        node.setLayoutY(y + dy);
        node.setScaleX(scale);
        node.setScaleY(scale);
    }

}