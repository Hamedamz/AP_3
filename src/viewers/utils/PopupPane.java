package viewers.utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import viewers.AppGUI;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class PopupPane extends Pane {
    private Scene containingScene;

    private Consumer<Stage> hideFunction;

    public PopupPane(Stage stage){
        containingScene = stage.getScene();
        Group root = (Group) stage.getScene().getRoot();
        root.getChildren().addAll(this);
    }

    public void hide() {
        ((Group) containingScene.getRoot()).getChildren().remove(this);
        if(hideFunction != null) {
            hideFunction.accept(AppGUI.getMainStage());
        }
    }

    public void setOnHide(Consumer<Stage> consumer){
        this.hideFunction = consumer;
    }


}
