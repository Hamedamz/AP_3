package viewers.utils;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import models.GameLogic.Entities.Buildings.Trap;
import models.GameLogic.Entities.Troop.Giant;
import models.GameLogic.Entities.Troop.Guardian;
import models.GameLogic.Entities.Troop.WallBreaker;
import models.interfaces.Attacker;
import models.interfaces.Destroyable;

public class ArrowShot extends Pane {

    private double width;
    private double height;
    private double layoutX;
    private double layoutY;
    private Circle arrow;

    public ArrowShot(Attacker attacker, Destroyable destroyable) {
        if (attacker instanceof Guardian || attacker instanceof Giant || attacker instanceof WallBreaker || attacker instanceof Trap) {
            return;
        }
        double toX = IsometricPane.getIsometricX(destroyable.getPosition()) + Const.ENTITY_TILE_WIDTH / 8;
        double toY = IsometricPane.getIsometricY(destroyable.getPosition()) + Const.ENTITY_TILE_HEIGHT / 8;
        double fromX = IsometricPane.getIsometricX(attacker.getPosition());
        double fromY = IsometricPane.getIsometricY(attacker.getPosition());

        width = Math.abs(fromX - toX);
        height = Math.abs(fromY - toY);

        layoutX = (toX + fromX - width) / 2;
        layoutY = (toY + fromY - height) / 2;

        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
        this.setPrefWidth(width);
        this.setHeight(height);

        arrow = new Circle(0, 0, 6, Color.CORNSILK);
        this.getChildren().add(arrow);

        MoveTo moveTo = new MoveTo(fromX - layoutX, fromY - layoutY);
        LineTo lineTo = new LineTo(toX - layoutX, toY - layoutY);
        Path path = new Path(moveTo, lineTo);
        PathTransition pathTransition = new PathTransition(new Duration(500), path);
        pathTransition.setNode(arrow);
        pathTransition.play();
        pathTransition.setOnFinished(event -> arrow.setVisible(false));

    }
}
