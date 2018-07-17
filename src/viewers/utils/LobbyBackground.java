package viewers.utils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.Random;

public class LobbyBackground extends Pane {
    private static LobbyBackground instance = new LobbyBackground();

    public static LobbyBackground getInstance() {
        return instance;
    }

    private ImageView cloads;

    public LobbyBackground() {
        for (int i = 0; i < 13; i++) {
            Image image = ImageLibrary.valueOf("LOBBY_" + i).getImage();
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(image.getWidth() * 2 / 3);
            imageView.setFitHeight(image.getHeight() * 2 / 3);

            int layoutY = 0;
            switch (i) {
                case 1:
                    cloads = imageView;
                    break;
                case 2:
                    layoutY = 346;
                    break;
                case 4:
                    layoutY = 432;
                    break;
                case 6:
                    layoutY = 394;
                    break;
                case 8:
                    layoutY = 496;
                    break;
                case 10:
                    layoutY = 552;
                    break;
                case 12:
                    layoutY = 544;
                    break;
            }
            imageView.setLayoutY(layoutY);

            if (i == 12) {
                imageView.setLayoutX(968);
            } else if (image.getHeight() < Const.WINDOW_HEIGHT) {
                animateTroop(imageView);
            }
            if (i != 1) {
                this.getChildren().add(imageView);
            } else {
                BackgroundSize backgroundSize = new BackgroundSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT, false, false, false, false);
                BackgroundImage backgroundImage = new BackgroundImage(cloads.getImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
                Pane pane = new Pane();
                pane.setMinSize(Const.WINDOW_WIDTH * 2, Const.WINDOW_HEIGHT);
                pane.setBackground(new Background(backgroundImage));
                this.getChildren().add(pane);

                Timeline timeline = new Timeline();
                timeline.setCycleCount(Animation.INDEFINITE);
                KeyValue keyValueL = new KeyValue(pane.layoutXProperty(), - Const.WINDOW_WIDTH);
                KeyFrame keyFrameL = new KeyFrame(new Duration(20000), keyValueL);
                timeline.getKeyFrames().add(keyFrameL);
                timeline.play();
            }
        }
    }

    private void animateTroop(ImageView imageView) {
        Random rand = new Random();
        rand.setSeed(System.nanoTime());
        int delay = rand.nextInt(5000);

        int fromX = rand.nextInt(400);
        int toX;
        double scale;
        if (fromX % 2 == 0) {
            fromX += 880;
            rand.setSeed(System.nanoTime());
            toX = rand.nextInt(400);
            scale = 1;
        } else {
            rand.setSeed(System.nanoTime());
            toX = 880 + rand.nextInt(400);
            scale = -1;
        }
        imageView.setScaleX(scale);
        imageView.setLayoutX(fromX);

        Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyValue keyValueL = new KeyValue(imageView.layoutXProperty(), toX);
        KeyFrame keyFrameL = new KeyFrame(new Duration(10000), keyValueL);

//        KeyValue keyValueS2 = new KeyValue(imageView.scaleXProperty(), scale * -1);
//        KeyFrame keyFrameS2 = new KeyFrame(new Duration(200), keyValueS2);

        KeyValue keyValueL2 = new KeyValue(imageView.layoutXProperty(), fromX);
        KeyFrame keyFrameL2 = new KeyFrame(new Duration(10000), keyValueL2);

        timeline.getKeyFrames().addAll(keyFrameL, keyFrameL2);
        timeline.play();
    }
}
