package viewers.utils.riverMenu;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import models.multiPlayer.ConnectionManager;
import models.ConnectionType;
import controllers.multiPlayer.Server;
import viewers.AppGUI;
import viewers.HostScene;
import viewers.utils.Const;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

import java.net.SocketException;

public class HostMenu extends StackPane {

    private static HostMenu instance = new HostMenu();
    public static HostMenu getInstance() {
        return instance;
    }

    private TextField portTextFiled;
    private TextField hostTextField;
    private RoundButton setUpButton;
    private RoundButton terminate;
    private Label log;

    private HostMenu() {
        portTextFiled = new TextField();
        portTextFiled.setPrefWidth(Const.SPACING * 8);
        portTextFiled.setText(String.valueOf(controllers.multiPlayer.utils.ServerConstants.SERVER_DEFAULT_PORT));

        hostTextField = new TextField();
        hostTextField.setText("localhost");
        hostTextField.setDisable(true);

        setUpButton = new RoundButton("Set Up", "green");

        terminate = new RoundButton("Terminate", "red");
        terminate.setVisible(false);

        portTextFiled.setOnKeyTyped(event -> {
            if (!portTextFiled.getText().isEmpty() && portTextFiled.getText().matches("\\d+")) {
                setUpButton.setDisable(false);
            } else {
                setUpButton.setDisable(true);
            }
        });

        setUpButton.setOnAction(event -> {
            AppGUI.getController().getSoundPlayer().play(Sounds.buttonSound);
            try {
                Server.initServer(Integer.parseInt(portTextFiled.getText()));
                ConnectionManager.getInstance().setConnectionType(ConnectionType.SERVER);
            } catch (SocketException e) {
                log.setTextFill(Color.RED);
                log.setText("Server Setup Unsuccessful!");
                return;
            }

            setUpButton.setVisible(false);
            terminate.setVisible(true);
            AppGUI.setStageScene(HostScene.getInstance());

        });

        log = new Label();

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        GridPane gridPane = new GridPane();
        gridPane.add(portTextFiled, 0,0);
        gridPane.add(hostTextField, 1,0);
        gridPane.add(log, 0,1, 2, 1);
        gridPane.add(setUpButton, 0,2);
        gridPane.add(terminate, 1,2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(Const.SPACING);
        gridPane.setHgap(Const.SPACING);
        this.getChildren().addAll(gridPane);
    }
}
