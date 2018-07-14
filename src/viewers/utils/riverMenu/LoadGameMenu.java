package viewers.utils.riverMenu;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import models.GameLogic.Exceptions.WrongPasswordException;
import viewers.AppGUI;
import viewers.MyVillageScene;
import viewers.utils.Const;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

import java.io.FileNotFoundException;

public class LoadGameMenu extends StackPane {
    private static LoadGameMenu instance = new LoadGameMenu();

    public static LoadGameMenu getInstance() {
        return instance;
    }

    private ComboBox namesComboBox;
    private RoundButton logInButton;
    private PasswordField passwordField;
    private Label log;
    private boolean isUserSet;
    private boolean isPasswordSet;

    public LoadGameMenu() {

        namesComboBox = new ComboBox();
        namesComboBox.setPrefWidth(Const.RIVER_MENU_SIZE);
        namesComboBox.setMaxWidth(Const.RIVER_MENU_SIZE);
        namesComboBox.setOnHidden(event -> {
            if (namesComboBox.getSelectionModel().isEmpty()) {
                isUserSet = false;
            } else {
                isUserSet = true;
            }
            checkInputs();
        });

        passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (checkInputs()) {
                        sendLogInRequest();
                    }
                    break;
            }
        });
        passwordField.setOnKeyTyped(event -> {
            if (passwordField.getText().isEmpty()) {
                isPasswordSet = false;
            } else {
                isPasswordSet = true;
            }
            checkInputs();
        });

        logInButton = new RoundButton("Log in", "green");
        logInButton.setDisable(true);
        logInButton.setOnAction(event -> sendLogInRequest());

        log = new Label();

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        GridPane gridPane = new GridPane();
        gridPane.add(namesComboBox, 0,0);
        gridPane.add(passwordField, 0,1);
        gridPane.add(log, 0,2);
        gridPane.add(logInButton, 0,3);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(Const.SPACING);
        gridPane.setHgap(Const.SPACING);

        this.getChildren().addAll(gridPane);
    }

    private void addExistingUsersToComboBox() {
        namesComboBox.getItems().clear();
        for (String name : AppGUI.getController().getWorld().getMyVillagesNameAndFile().values()) {
            namesComboBox.getItems().add(name);
        }
    }

    public void reset() {
        isUserSet = false;
        isPasswordSet = false;
        log.setText("");
        passwordField.setText("");
        addExistingUsersToComboBox();
    }

    public boolean checkInputs() {
        if (isUserSet && (isPasswordSet || !passwordField.getText().isEmpty())) {
            logInButton.setDisable(false);
        } else {
            logInButton.setDisable(true);
        }
        return isUserSet && isPasswordSet;
    }

    private void sendLogInRequest() {
        String name = (String) namesComboBox.getSelectionModel().getSelectedItem();
        String password = passwordField.getText();

        try {
            AppGUI.getController().loadGame(name, password);
        } catch (WrongPasswordException | FileNotFoundException e) {
            log.setText(e.getMessage());
            return;
        }

        loadVillage();
    }

    private void loadVillage() {
        MyVillageScene.getInstance().reBuild();
        AppGUI.setStageScene(MyVillageScene.getInstance());
        SoundPlayer.play(Sounds.loadSound);
        SoundPlayer.playBackground(Sounds.mainSound);
    }
}
