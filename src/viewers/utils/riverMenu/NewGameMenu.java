package viewers.utils.riverMenu;

import controllers.Controller;
import controllers.Exceptions.VillageAlreadyExists;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import models.multiPlayer.ConnectionManager;
import models.ConnectionType;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

public class NewGameMenu extends StackPane {
    private static NewGameMenu instance = new NewGameMenu();

    private TextField nameField;
    private PasswordField passwordField;
    private RoundButton newGameButton;
    private GridPane body;
    private Label log;

    public static NewGameMenu getInstance() {
        return instance;
    }

    public NewGameMenu() {
        nameField = new TextField();
        nameField.setOnKeyTyped(event -> checkInputs());
        passwordField = new PasswordField();
        passwordField.setOnKeyTyped(event -> checkInputs());

        newGameButton = new RoundButton("New Game", "green");
        newGameButton.setDisable(true);

        log = new Label();

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        body = new GridPane();
        body.add(nameField, 0,0);
        body.add(passwordField, 0,1);
        body.add(log, 0,2);
        body.add(newGameButton, 0,3);
        body.setAlignment(Pos.CENTER);
        body.setVgap(Const.SPACING);
        this.getChildren().addAll(body);
    }

    private boolean newGame() {
        try {
            Controller.getController().newGame(nameField.getText(), passwordField.getText());
        } catch (VillageAlreadyExists e) {
            log.setTextFill(Color.RED);
            log.setText(e.getMessage());
            return false;
        }
        log.setTextFill(Color.DODGERBLUE);
        log.setText("new game created!");
        return true;
    }

    private boolean checkInputs() {
        boolean condition = !nameField.getText().isEmpty() && !passwordField.getText().isEmpty();
        if (condition) {
            newGameButton.setDisable(false);
        } else {
            newGameButton.setDisable(true);
        }
        return condition;
    }

    public void reset() {
        this.getChildren().clear();
        this.getChildren().addAll(body);
        nameField.setText("");
        passwordField.setText("");
        log.setText("");
        checkInputs();
    }

    public void rebuildForClientMenu() {
        newGameButton.setOnAction(event -> {
            AppGUI.getController().getSoundPlayer().play(Sounds.buttonSound);
            if (newGame()) {
                ClientMenu.getInstance().disableLoginOrNewGame(true);
            }
        });
    }

    public void rebuildForNewGameMenu() {
        newGameButton.setOnAction(event -> {
            AppGUI.getController().getSoundPlayer().play(Sounds.buttonSound);
            if (newGame()) {
                ConnectionManager.getInstance().setConnectionType(ConnectionType.SINGLE_PLAYER);
                AppGUI.loadVillageScene();
            }
        });
    }

    public GridPane getBody() {
        return body;
    }
}
