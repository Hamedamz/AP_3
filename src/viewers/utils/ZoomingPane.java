package viewers.utils;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class ZoomingPane extends Pane {
    private Translate initialLayout;
    private Scale scale;
    private double minScale;
    private double maxScale = Double.MAX_VALUE;

    public ZoomingPane(Node... children) {
        super(children);
        scale = new Scale(1, 1);
        initialLayout = new Translate(getLayoutX(), getLayoutY());
        scale.setPivotX(0);
        scale.setPivotY(0);
        getTransforms().addAll(scale);
    }

    final void zoomAroundPivot(Double zoomFactor, double screenX, double screenY) {
        double prevPivotX = scale.getPivotX();
        double prevPivotY = scale.getPivotY();
        double prevScaleX = scale.getX();
        double prevScaleY = scale.getY();
        double prevTranslateX = getLayoutX() - initialLayout.getX();
        double prevTranslateY = getLayoutY() - initialLayout.getY();
        if (zoomFactor * prevScaleX <= minScale || zoomFactor * prevScaleY >= maxScale) {
            return;
        }
        scale.setPivotX(prevPivotX - prevPivotX / prevScaleX - prevTranslateX / prevScaleX + screenX / prevScaleX);
        scale.setPivotY(prevPivotY - prevPivotY / prevScaleY - prevTranslateY / prevScaleY + screenY / prevScaleY);
        scale.setX(zoomFactor * prevScaleX);
        scale.setY(zoomFactor * prevScaleY);
        setLayoutX(screenX - scale.getPivotX());
        setLayoutY(screenY - scale.getPivotY());
    }

    public double getMinScale() {
        return minScale;
    }

    public void setMinScale(double minScale) {
        this.minScale = minScale;
    }

    public double getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(double maxScale) {
        this.maxScale = maxScale;
    }
}
