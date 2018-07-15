package viewers.utils.riverMenu;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    private GridPane body;

    public LoadGameMenu() {

        namesComboBox = new ComboBox();
        namesComboBox.setPrefWidth(Const.RIVER_MENU_SIZE);
        namesComboBox.setMaxWidth(Const.RIVER_MENU_SIZE);
        namesComboBox.setOnHidden(event -> checkInputs());

        passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.setOnKeyTyped(event -> checkInputs());

        logInButton = new RoundButton("Log in", "green");
        logInButton.setDisable(true);
        logInButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            sendLogInRequest();
        });

        log = new Label();

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        body = new GridPane();
        body.add(namesComboBox, 0,0);
        body.add(passwordField, 0,1);
        body.add(log, 0,2);
        body.add(logInButton, 0,3);
        body.setAlignment(Pos.CENTER);
        body.setVgap(Const.SPACING);
        body.setHgap(Const.SPACING);

        this.getChildren().addAll(body);
    }

    private void addExistingUsersToComboBox() {
        namesComboBox.getItems().clear();
        for (String name : AppGUI.getController().getWorld().getMyVillagesNameAndFile().values()) {
            namesComboBox.getItems().add(name);
        }
    }

    private boolean sendLogInRequest() {
        String name = (String) namesComboBox.getSelectionModel().getSelectedItem();
        String password = passwordField.getText();

        try {
            AppGUI.getController().loadGame(name, password);
        } catch (WrongPasswordException | FileNotFoundException e) {
            log.setTextFill(Color.RED);
            log.setText(e.getMessage());
            return false;
        }
        log.setTextFill(Color.DODGERBLUE);
        log.setText("logged in as" + name);
        return true;
    }

    public void reset() {
        this.getChildren().clear();
        this.getChildren().addAll(body);
        log.setText("");
        passwordField.setText("");
        addExistingUsersToComboBox();
        checkInputs();
    }

    public boolean checkInputs() {
        boolean condition = !namesComboBox.getSelectionModel().isEmpty() && !passwordField.getText().isEmpty();
        if (condition) {
            logInButton.setDisable(false);
        } else {
            logInButton.setDisable(true);
        }
        return condition;
    }

    public void rebuildForClientMenu() {
        logInButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            if (sendLogInRequest()) {
                ClientMenu.getInstance().disableLoginOrNewGame(true);
            }
        });

        passwordField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (checkInputs()) {
                        if (sendLogInRequest()) {
                            ClientMenu.getInstance().disableLoginOrNewGame(true);
                        }
                    }
                    break;
            }
        });
    }

    public void rebuildForLoadGameMenu() {
        logInButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            if (sendLogInRequest()) {
                AppGUI.loadVillageScene();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (checkInputs()) {
                        if (sendLogInRequest()) {
                            AppGUI.loadVillageScene();
                        }
                    }
                    break;
            }
        });
    }

    public GridPane getBody() {
        return body;
    }
}
