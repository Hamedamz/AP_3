package viewers.oldScenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import viewers.AppGUI;
import viewers.utils.Const;

public class GameScene extends Scene {
    private static GameScene instance = new GameScene();

    public static GameScene getInstance() {
        return instance;
    }

    private GameScene() {
        super(new Group(), Const.WINDOW_WIDTH/2, Const.WINDOW_HEIGHT/2);
        Group root = (Group) getRoot();
        VBox vBox = new VBox();

        root.getChildren().addAll(vBox);

        Button singlePlayerButton = new Button("SinglePlayer");
        Button multiPlayerButton = new Button("multiPlayer");
        Button options = new Button("Options");
        Button quit = new Button("Quit");

        vBox.getChildren().addAll(singlePlayerButton, multiPlayerButton, options, quit);

        singlePlayerButton.setOnAction(event -> AppGUI.setStageScene(SinglePlayerGameScene.getInstance()));
        multiPlayerButton.setOnAction(event -> {AppGUI.setStageScene(MultiPlayerGameScene.getInstance());}); // TODO: 7/9/2018
        options.setOnAction(event -> {}); // TODO: 7/10/2018
        quit.setOnAction(event -> {
            AppGUI.getMainStage().close();
        });
    }



}
