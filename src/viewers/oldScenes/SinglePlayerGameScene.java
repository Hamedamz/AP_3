package viewers.oldScenes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import viewers.AppGUI;
import viewers.MyVillageScene;
import viewers.utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static viewers.utils.Const.WINDOW_HEIGHT;
import static viewers.utils.Const.WINDOW_WIDTH;

public class SinglePlayerGameScene extends Scene {
    private static SinglePlayerGameScene instance = new SinglePlayerGameScene();

    private SinglePlayerGameScene() {
        super(new Group(), WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
        Group root = (Group) getRoot();
        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");
        Button backButton = new Button("Back");

        HBox hBox = new HBox(newGameButton, loadGameButton, backButton);

        root.getChildren().addAll(hBox);

        newGameButton.setOnAction(event -> {
            AppGUI.loadVillageScene();
            loadStage();
        });

        loadGameButton.setOnAction(event -> {
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
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("savedMaps"));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jSon files", "*.json"));
                List<File> selectedFiles = fileChooser.showOpenMultipleDialog(AppGUI.getMainStage());
                if (selectedFiles != null) {
                    for (File selectedFile : selectedFiles) {
                        try {
                            AppGUI.getController().loadGameFromFile(selectedFile);
                            AppGUI.loadVillageScene();
                            loadStage();
                        } catch (FileNotFoundException e) {
                            AppGUI.getMyVillageScene().handleException(e);
                        }
                    }
                }
//                try {
//                    if (loadingVillageName.get() != null) {
//                     //   AppGUI.getController().loadGame(AppGUI.getController().getWorld().getMyVillagesNameAndFile().get(loadingVillageName.get()));
//                        // FIXME: 7/10/18 correct above
//                        //loadStage();
//                    }
//                    // FIXME: 7/9/2018
//                } catch (Exception e) {
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

    public static SinglePlayerGameScene getInstance() {
        return instance;
    }

    private void loadStage() {
        AppGUI.getMainStage().setX(10);
        AppGUI.getMainStage().setY(10);
        AppGUI.getController().getSoundPlayer().play(Sounds.loadSound);
        AppGUI.getController().getSoundPlayer().playBackground(Sounds.mainSound);
    }
}
