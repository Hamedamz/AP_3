package viewers;

import controllers.Controller;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.GameLogic.Village;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.VillageConsole;

public class AppGUI extends Application {
    private static Stage mainStage;

    public static VillageScene getVillageScene() {
        return VillageScene.getInstance();
    }

    public static Controller getController() {
        return Controller.getController();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        getMainStage().setResizable(false);
        setStageScene(GameScene.getInstance());
        SoundPlayer.playBackground(Sounds.mainSound);
        getMainStage().show();

    }

    public static void setStageScene(Scene scene) {
        AppGUI.getMainStage().setScene(scene);
        getMainStage().sizeToScene();
    }
}
