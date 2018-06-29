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


    public ProgressBar(double width, double height, double percentage) {
        this.width = width;
        this.height = height;
        this.percentage = percentage;
        bar = new Rectangle(width * percentage, height, Color.SEAGREEN);
        background = new Rectangle(width + 2, height + 2, Color.BLACK);
        background.setOpacity(0.5);
    }


}
