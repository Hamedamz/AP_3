package viewers.utils;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class DraggablePane extends StackPane {
    private double startX;
    private double startY;

    public DraggablePane(Node... children) {
        super(children);
    }

    public void initialize() {
        this.setOnMousePressed(event -> {
            startX = event.getSceneX() - this.getLayoutX();
            startY = event.getSceneY() - this.getLayoutY();
        });

        this.setOnMouseDragged(event -> {
            this.setLayoutX(event.getSceneX() - startX);
            this.setLayoutY(event.getSceneY() - startY);
        });

        this.setOnScroll(event -> {
            if (event.getDeltaY() / 100 + this.getScaleX() > 0) {
                this.setScaleX(event.getDeltaY() / 100 + this.getScaleX());
                this.setScaleY(event.getDeltaY() / 100 + this.getScaleY());
            }
        });

    }
}
