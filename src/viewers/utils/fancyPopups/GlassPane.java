package viewers.utils.fancyPopups;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import viewers.utils.Const;
import viewers.utils.StrokeText;

public abstract class GlassPane extends StackPane {
    StrokeText title;
    VBox body;
    private Rectangle background;

    public GlassPane(String title) {
        this.title = new StrokeText(title);
        this.setHeight(Const.WINDOW_HEIGHT);
        this.setWidth(Const.WINDOW_WIDTH);
        background = new Rectangle(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT, Color.BLACK);
        background.setOpacity(0.75);

        body = new VBox(Const.SPACING * 3);
        body.setAlignment(Pos.CENTER);
        body.setMinHeight(Const.WINDOW_HEIGHT);
        body.setMinWidth(Const.WINDOW_WIDTH);
        this.getChildren().clear();
        this.getChildren().addAll(background, body);
    }

    public abstract void setProperties();
}
