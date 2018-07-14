package viewers.utils.riverMenu;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import models.multiPlayer.Server;
import viewers.AppGUI;
import viewers.HostScene;
import viewers.utils.Const;
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
        portTextFiled.setText(String.valueOf(models.multiPlayer.utils.ServerConstants.SERVER_DEFAULT_PORT));

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
            // TODO: 7/14/2018 set up server if successful open host scene
            try {
                Server.initServer(Integer.parseInt(portTextFiled.getText()));
            } catch (SocketException e) {
                log.setText("Server Setup Unsuccessful!");
            }
            try {
                Server.initServer(Integer.parseInt(portTextFiled.getText()));
            } catch (SocketException e) {
                e.printStackTrace();
            }
            boolean isSetUp = true;
            if (isSetUp)
            {
                setUpButton.setVisible(false);
                terminate.setVisible(true);
                AppGUI.setStageScene(HostScene.getInstance());
            }
        });

        log = new Label();
        log.setTextFill(Color.RED);

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        GridPane gridPane = new GridPane();
        gridPane.add(portTextFiled, 0,0);
        gridPane.add(hostTextField, 1,0);
        gridPane.add(log, 0,1);
        gridPane.add(setUpButton, 0,2);
        gridPane.add(terminate, 1,2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(Const.SPACING);
        gridPane.setHgap(Const.SPACING);
        this.getChildren().addAll(gridPane);
    }
}
