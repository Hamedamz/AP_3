package viewers.utils;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Pair;

import static viewers.utils.Const.*;

public class MapBrowserPane extends ZoomingPane {
    private double startX;
    private double startY;

    public MapBrowserPane(Node... children) {
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
                setLayoutX(event.getSceneX() - startX);

            }
            if (isLayoutYValid(event.getSceneY() - startY)) {
                setLayoutY(event.getSceneY() - startY);

            }
        });

        this.setOnScroll(event -> {
//            if (isScaleYValid(event.getDeltaY() / 100 + this.getZoomFactor()) && isScaleXValid(event.getDeltaY() / 100 + this.getZoomFactor())) {
//                new ZoomOperator().zoom(this, event.getDeltaY() / 100 + this.getScaleX(), event.getScreenX(), event.getScreenY());
//                this.setScaleX(event.getDeltaY() / 100 + this.getScaleX());
//                this.setScaleY(event.getDeltaY() / 100 + this.getScaleY());
                double delta = 1.3;
                zoomAroundPivot(Math.pow(delta, event.getDeltaY() / 100) , event.getScreenX(), event.getScreenY());
//            }
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


