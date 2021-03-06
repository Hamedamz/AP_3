package viewers.oldScenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import viewers.AppGUI;
import viewers.utils.*;

import java.util.concurrent.atomic.AtomicReference;

import static viewers.utils.Const.WINDOW_HEIGHT;
import static viewers.utils.Const.WINDOW_WIDTH;

public class MultiPlayerGameScene extends Scene {
    private static MultiPlayerGameScene instance = new MultiPlayerGameScene();

    private MultiPlayerGameScene() {
        super(new Group(), WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
        Group root = (Group) getRoot();
        Button hostButton = new Button("Host");
        Button joinButton = new Button("Join");
        Button backButton = new Button("Back");

        VBox vBox = new VBox(hostButton, joinButton, backButton);

        root.getChildren().addAll(vBox);

        hostButton.setOnAction(event -> {

        });

        joinButton.setOnAction(event -> {
            PopupPane menuPane = new PopupPane(AppGUI.getMainStage());
            menuPane.setLayoutX(WINDOW_WIDTH/3);
            menuPane.setLayoutY(WINDOW_HEIGHT/3);
            ChoicePane choicePane = new ChoicePane(WINDOW_WIDTH/3);

            //choicePane head
            Button closeButton = new Button("X");
            closeButton.setOnAction(event1 -> menuPane.hide());
            choicePane.getHead().getChildren().addAll(closeButton);

            //choicePane choice components
            AtomicReference<String> loadingVillageName = new AtomicReference<>();
            for (String villageName : AppGUI.getController().getWorld().getMyVillagesNameAndFile().values()) {
                Button button = new Button(villageName);
                button.setOnAction(event2 -> {
                    loadingVillageName.set(villageName);
                });
                choicePane.getChoiceComponents().getChildren().add(button);
            }
            menuPane.getChildren().addAll(choicePane);

            //choicePane tail
            Button loadButton = new Button("Load");
            Button cancelButton = new Button("Cancel");

            loadButton.setOnAction(event1 -> {
//                try {
//                    if (loadingVillageName.get() != null) {
//                        AppGUI.getController().loadGame(loadingVillageName.get());  // FIXME: 7/14/18 hamedamz
////                        loadStage();
//                    }
//                    // FIXME: 7/9/2018
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
            });

            cancelButton.setOnAction(event2 -> menuPane.hide());

            choicePane.getTail().getChildren().addAll(loadButton, cancelButton);
        });

        backButton.setOnAction(event -> {
            AppGUI.setStageScene(GameScene.getInstance());
        });
    }

    public static MultiPlayerGameScene getInstance() {
        return instance;
    }

    private void loadStage() {
        AppGUI.getMainStage().setX(10);
        AppGUI.getMainStage().setY(10);
        AppGUI.getController().getSoundPlayer().play(Sounds.loadSound);
        AppGUI.getController().getSoundPlayer().playBackground(Sounds.mainSound);
    }
}
