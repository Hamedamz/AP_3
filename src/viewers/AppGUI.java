package viewers;

import controllers.Controller;
import controllers.JsonHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppGUI extends Application {
    private static Stage mainStage;

    public static MyVillageScene getMyVillageScene() {
        return MyVillageScene.getInstance();
    }

    public static BattleGroundScene getBattleGroundScene() {
        return BattleGroundScene.getInstance();
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
        getMainStage().setOnHidden(event -> JsonHandler.saveAccount(Controller.getController().getWorld().getAccount(), "UIVillage"));
        getMainStage().show();

    }

    public static void setStageScene(Scene scene) {
        AppGUI.getMainStage().setScene(scene);
        getMainStage().sizeToScene();
    }
}
