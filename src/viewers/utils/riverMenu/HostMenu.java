package viewers.utils.riverMenu;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import models.multiPlayer.Server;
import viewers.AppGUI;
import viewers.HostScene;
import viewers.utils.Const;
import viewers.utils.fancyButtons.RoundButton;

public class HostMenu extends StackPane {

    private static HostMenu instance = new HostMenu();
    public static HostMenu getInstance() {
        return instance;
    }

    private TextField portTextFiled;
    private TextField hostTextField;
    private RoundButton setUpButton;
    private RoundButton terminate;

    public HostMenu() {
        portTextFiled = new TextField();
        portTextFiled.setPrefWidth(Const.SPACING * 8);
        portTextFiled.setText(String.valueOf(models.multiPlayer.utils.ServerConstants.SERVER_DEFAULT_PORT));

        hostTextField = new TextField();
//        hostTextField.setText(Server.getInstance().getSocket().getInetAddress().toString());
        hostTextField.setText("localhost");
        hostTextField.setDisable(true);

        setUpButton = new RoundButton("Set Up", "green");

        terminate = new RoundButton("Terminate", "red");
        terminate.setVisible(false);

        portTextFiled.setOnKeyTyped(event -> {
            if (!portTextFiled.getText().equals("")) {
                setUpButton.setDisable(false);
            } else {
                setUpButton.setDisable(true);
            }
        });

        setUpButton.setOnAction(event -> {
            // TODO: 7/14/2018 set up server if successful open host scene
            boolean isSetUp = true;
            if (isSetUp)
            {
                setUpButton.setVisible(false);
                terminate.setVisible(true);
                AppGUI.setStageScene(HostScene.getInstance());
            }
        });

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        GridPane gridPane = new GridPane();
        gridPane.add(portTextFiled, 0,0);
        gridPane.add(hostTextField, 1,0);
        gridPane.add(setUpButton, 0,1);
        gridPane.add(terminate, 1,1);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(Const.SPACING);
        gridPane.setHgap(Const.SPACING);
        this.getChildren().addAll(gridPane);
    }
}