package viewers;

import controllers.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppGUI extends Application {
    private static Stage mainStage;
    private static VillageScene villageScene = VillageScene.getInstance();

    public static VillageScene getVillageScene() {
        return villageScene;
    }

    public static Controller getController() {
        return Controller.getController();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = new Stage();
        MenuPopup.setStage(getMainStage());
        //getMainStage().setTitle("");
        getMainStage().setResizable(false);
        setStageScene(villageScene);
        getMainStage().show();
    }

    public static void setStageScene(Scene scene) {
        AppGUI.getMainStage().setScene(scene);
        getMainStage().sizeToScene();
    }
}
