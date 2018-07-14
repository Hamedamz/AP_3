package viewers.utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import viewers.utils.riverMenu.RiverMenu;

import static viewers.utils.Const.*;

public class GameLobbyScene extends Scene {
    private static GameLobbyScene instance = new GameLobbyScene();

    public static GameLobbyScene getInstance() {
        return instance;
    }

    private Group root;
    private ImageView background;

    public GameLobbyScene() {
        super(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        root = (Group) getRoot();
        this.getStylesheets().add("/viewers/styles/game.css");
        build();
    }

    private void build() {
//        background = new ImageView(ImageLibrary);

        root.getChildren().clear();
        root.getChildren().addAll(RiverMenu.getInstance());
    }
}
