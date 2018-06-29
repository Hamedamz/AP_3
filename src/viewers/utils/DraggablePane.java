package viewers.utils;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Pair;

import static viewers.utils.Const.*;

public class DraggablePane extends ZoomingPane {
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
                double delta = 1.1;
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

class ZoomingPane extends Pane {
    private Translate initialLayout;
    private Scale scale;

    public ZoomingPane(Node... children) {
        super(children);
        scale = new Scale(1, 1);
        initialLayout = new Translate(getLayoutX(), getLayoutY());
        scale.setPivotX(getLayoutX());
        scale.setPivotY(getLayoutY());
        getTransforms().addAll(scale);
    }

//    protected void layoutChildren() {
//        Pos pos = Pos.TOP_LEFT;
//        double width = getWidth();
//        double height = getHeight();
//        double top = getInsets().getTop();
//        double right = getInsets().getRight();
//        double left = getInsets().getLeft();
//        double bottom = getInsets().getBottom();
//        double contentWidth = (width - left - right)/zoomFactor.get();
//        double contentHeight = (height - top - bottom)/zoomFactor.get();
//        layoutInArea(this, left, top,
//                contentWidth, contentHeight,
//                0, null,
//                pos.getHpos(),
//                pos.getVpos());
//    }


    final void zoomAroundPivot(Double zoomFactor, double screenX, double screenY) {
        double prevPivotX = scale.getPivotX();
        double prevPivotY = scale.getPivotY();
        double prevScaleX = scale.getX();
        double prevScaleY = scale.getY();
        double prevTranslateX = getLayoutX() - initialLayout.getX();
        double prevTranslateY = getLayoutY() - initialLayout.getY();
        System.err.println(getLayoutX() + " " + getLayoutY());
        scale.setPivotX(prevPivotX - prevPivotX/prevScaleX  - prevTranslateX/prevScaleX + screenX/prevScaleX);
        scale.setPivotY(prevPivotY - prevPivotY/prevScaleY  - prevTranslateY/prevScaleY + screenY/prevScaleY);
        scale.setX(zoomFactor * prevScaleX);
        scale.setY(zoomFactor * prevScaleY);
        setLayoutX(screenX - scale.getPivotX());
        setLayoutY(screenY - scale.getPivotY());
    }

}


