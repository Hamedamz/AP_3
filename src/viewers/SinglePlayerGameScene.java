package viewers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import viewers.utils.ChoicePane;
import viewers.utils.PopupPane;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;

import java.io.FileNotFoundException;
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

        HBox hBox = new HBox(newGameButton, loadGameButton);

        root.getChildren().addAll(hBox);

        newGameButton.setOnAction(event -> {
            AppGUI.setStageScene(VillageScene.getInstance());
            SoundPlayer.play(Sounds.loadSound);
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
            for (String villageName : AppGUI.getController().getSinglePlayerWorld().getMyVillagesNameAndPath().keySet()) {
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
                try {
                    if (loadingVillageName.get() != null) {
                        AppGUI.getController().loadGame(AppGUI.getController().getSinglePlayerWorld().getMyVillagesNameAndPath().get(loadingVillageName.get()));
                    }
                    // FIXME: 7/9/2018
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });

            cancelButton.setOnAction(event2 -> menuPane.hide());

            choicePane.getTail().getChildren().addAll(loadButton, cancelButton);
        });




    }

    public static SinglePlayerGameScene getInstance() {
        return instance;
    }
}
