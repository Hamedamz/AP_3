package viewers;

import controllers.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppGUI extends Application {
    private static Stage mainStage;

    private static Controller getController() {
        return Controller.getController();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = new Stage();
        //getMainStage().setTitle("");
        getMainStage().setResizable(false);
        setStageScene(VillageScene.getInstance());
        getMainStage().show();
    }

    public static void setStageScene(Scene scene) {
        AppGUI.getMainStage().setScene(scene);
        getMainStage().sizeToScene();

    }
}
