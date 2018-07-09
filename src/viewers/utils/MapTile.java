package viewers.utils;

import controllers.Exceptions.InvalidInputException;
import javafx.animation.FadeTransition;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import models.GameLogic.Exceptions.CountLimitReachedException;
import models.GameLogic.Exceptions.InvalidPositionException;
import models.GameLogic.Exceptions.NoFreeBuilderException;
import models.GameLogic.Exceptions.NotEnoughResourcesException;
import viewers.AppGUI;

public class MapTile extends Rectangle {

    private int mapX;
    private int mapY;

    public MapTile(double width, double height, int x, int y) {
        super(width, height);
        this.mapX = x;
        this.mapY = y;

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(0.2);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), this);
        fadeOut.setFromValue(0.2);
        fadeOut.setToValue(0);

        this.setOpacity(0);
//        this.setOnMouseEntered(event -> fadeIn.play());
//        this.setOnMouseExited(event -> fadeOut.play());

        this.setOnDragOver(event -> {
            /* data is dragged over the target */
            /* accept it only if it is not dragged from the same node
             * and if it has a string data */
            if (event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        this.setOnDragEntered(event -> {
            if (event.getDragboard().hasString()) {
                fadeIn.play();
            }

            event.consume();
        });

        this.setOnDragExited(event -> fadeOut.play());

        this.setOnDragDropped(event -> {
            /* data dropped */
            /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                try {
                    AppGUI.getController().buildBuilding(db.getString(), this.mapX, this.mapY);
                } catch (InvalidInputException | InvalidPositionException | CountLimitReachedException | NotEnoughResourcesException | NoFreeBuilderException e) {
                    e.printStackTrace();
                }
//                System.out.println("new building" + db.getString() + "in" + this.mapX + " " + this.mapY);
                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        });
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }
}
