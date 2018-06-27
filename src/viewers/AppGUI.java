package viewers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppGUI extends Application {
    private static Stage mainStage;
    // FIXME: 6/27/2018 Hamed Alimohammad Zadeh
    //we need an instance of a main Controller here for sending users request to Controller
    //plz use static field for it and a public getter

    public static Stage getMainStage() {
        return mainStage;
    }




    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = new Stage();
        //getMainStage().setTitle("");
        getMainStage().setResizable(false);
        // FIXME: 6/27/2018 Hamed Alimohammad Zadeh
        //setStageScene();
        getMainStage().show();
    }

    public static void setStageScene(Scene scene) {
        AppGUI.getMainStage().setScene(scene);
        // FIXME: 6/27/2018 Hamed Alimohammad Zadeh
        getMainStage().sizeToScene();

    }
}
