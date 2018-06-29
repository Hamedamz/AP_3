package viewers.utils;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ProgressBar extends StackPane {
    private double width;
    private double height;
    private double percentage;
    private Rectangle bar;
    private Rectangle background;
    private Color color = Color.LIMEGREEN;

    public ProgressBar(double width, double height, double percentage) {
        this.width = width;
        this.height = height;
        this.percentage = percentage;
        bar = new Rectangle(width * percentage, height, this.color);
        background = new Rectangle(width + 2, height + 2, Color.BLACK);
        background.setOpacity(0.5);
        this.setMaxSize(width + 2, height + 2);
        this.getChildren().addAll(background, bar);
    }

    public void update(double percentage) {
        this.percentage = percentage;
        bar.setWidth(width * this.percentage);
        if (this.percentage == 1) {
            this.setFinished();
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private void setFinished() {
        this.setVisible(false);
    }
}
