package viewers;

import controllers.Controller;
import controllers.JsonHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;

import java.util.Optional;

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
        setStageScene(GameLobbyScene.getInstance());

        getMainStage().setOnHiding(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Game");
            alert.setHeaderText(null);
            alert.setContentText("Save the game before closing?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                getController().saveGame();
            }

        });
        getMainStage().show();
    }

    public static void setStageScene(Scene scene) {
        AppGUI.getMainStage().setX(0);
        AppGUI.getMainStage().setY(0);
        AppGUI.getMainStage().setScene(scene);
        getMainStage().sizeToScene();
    }

    public static void loadVillageScene() {
        MyVillageScene.getInstance().reBuild();
        AppGUI.setStageScene(MyVillageScene.getInstance());
        SoundPlayer.play(Sounds.loadSound);
        SoundPlayer.playBackground(Sounds.mainSound);
    }

}
