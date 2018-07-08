package viewers;

import javafx.scene.control.Button;

public class GameScene {
    private static GameScene instance = new GameScene();

    private GameScene() {


    }

    public static GameScene getInstance() {
        return instance;
    }
}
