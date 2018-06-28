package viewers.utils;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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
            if (isScaleValid(event.getDeltaY() / 100 + this.getScaleX())) {
                this.setScaleX(event.getDeltaY() / 100 + this.getScaleX());
                this.setScaleY(event.getDeltaY() / 100 + this.getScaleY());
            }
        });

    }

    private boolean isScaleValid(double scale) {
        return scale > 0 && this.getMaxHeight() * scale >= WINDOW_HEIGHT && this.getMaxWidth() * scale >= WINDOW_WIDTH;
    }

    private boolean isLayoutXValid(double layoutX) {
        return layoutX >= WINDOW_WIDTH - this.getMaxWidth() && layoutX <= 0;
    }

    private boolean isLayoutYValid(double layoutY) {
        return layoutY >= WINDOW_HEIGHT - this.getMaxHeight() && layoutY <= 0;
    }
}
