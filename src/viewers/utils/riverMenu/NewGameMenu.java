package viewers.utils.riverMenu;

import controllers.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import viewers.AppGUI;
import viewers.MyVillageScene;
import viewers.utils.Const;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

public class NewGameMenu extends StackPane {
    private static NewGameMenu instance = new NewGameMenu();

    private TextField nameField;
    private TextField passwordField;
    private RoundButton newGameButton;

    public static NewGameMenu getInstance() {
        return instance;
    }

    public NewGameMenu() {
        nameField = new TextField();
        passwordField = new TextField();

        newGameButton = new RoundButton("New Game", "green");
        newGameButton.setOnAction(event -> {
            // TODO: 7/14/2018 handle account data @svt
            SoundPlayer.play(Sounds.buttonSound);
            Controller.getController().newGame(nameField.getText(), passwordField.getText());
            MyVillageScene.getInstance().reBuild();
            AppGUI.setStageScene(MyVillageScene.getInstance());
            SoundPlayer.play(Sounds.loadSound);
            SoundPlayer.playBackground(Sounds.mainSound);
        });

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        GridPane gridPane = new GridPane();
        gridPane.add(nameField, 0,0);
        gridPane.add(passwordField, 0,1);
        gridPane.add(newGameButton, 0,2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(Const.SPACING);
        this.getChildren().addAll(gridPane);
    }
}
