package viewers.utils.riverMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.multiPlayer.Client;
import models.multiPlayer.utils.ClientConstants;
import models.multiPlayer.utils.ServerConstants;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.fancyButtons.RoundButton;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientMenu extends StackPane {

    private static ClientMenu instance = new ClientMenu();

    public static ClientMenu getInstance() {
        return instance;
    }

    private TextField clientPort;
    private TextField clientAddress;
    private TextField serverPort;
    private TextField serverAddress;
    private RoundButton joinButton;
    private Label log;
    private GridPane newGameMenu = NewGameMenu.getInstance().getBody();
    private GridPane loginMenu = LoadGameMenu.getInstance().getBody();
    private GridPane gridPane;
    private GridPane body;

    public ClientMenu() {

        Text clientLabel = new Text("Client:");
        clientPort = new TextField();
        clientPort.setPrefWidth(Const.SPACING * 8);
        clientPort.setOnKeyTyped(event -> checkInputs());
        clientAddress = new TextField();
        clientAddress.setDisable(true);

        Text serverLabel = new Text("Server:");
        serverPort = new TextField();
        serverPort.setPrefWidth(Const.SPACING * 8);
        serverPort.setOnKeyTyped(event -> checkInputs());
        serverAddress = new TextField();
        serverAddress.setPromptText("Server Address");
        serverAddress.setOnKeyTyped(event -> checkInputs());

        joinButton = new RoundButton("Join", "green");
        joinButton.setDisable(true);
        joinButton.setOnAction(event -> {
            try {
                Client.initClient(Integer.parseInt(clientPort.getText()));
                try {
                    Client.getInstance().setupConnection(InetAddress.getByName(serverAddress.getText()), Integer.parseInt(serverPort.getText()));
                    clientAddress.setText(Client.getInstance().getSocket().getInetAddress().getHostName());
                    AppGUI.loadVillageScene();
                } catch (UnknownHostException e) {
                    log.setText("Unable to Setup Connection");
                }
            } catch (SocketException e) {
                log.setText("Client Setup Unsuccessful!");
            }

        });

        log = new Label();

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        gridPane = new GridPane();
        gridPane.add(new Text("login"), 0,0);
        gridPane.add(new Text("or"), 1,0);
        gridPane.add(new Text("create new game"), 2,0);
        gridPane.add(loginMenu, 0,1);
        gridPane.add(newGameMenu, 2,1);
        gridPane.setVgap(Const.SPACING);
        gridPane.setHgap(Const.SPACING);
        gridPane.setPadding(new Insets(0, 0, Const.SPACING * 3, 0));
        body = new GridPane();
        body.add(gridPane, 0, 0, 2, 1);
        body.add(clientPort, 0,1);
        body.add(clientAddress, 1, 1);
        body.add(serverPort, 0,2);
        body.add(serverAddress, 1,2);
        body.add(log, 0,3);
        body.add(joinButton, 0,4);
        body.setAlignment(Pos.CENTER);
        body.setVgap(Const.SPACING);
        body.setHgap(Const.SPACING);
        this.getChildren().addAll(body);
    }

    private boolean checkInputs() {
        boolean condition = !clientPort.getText().isEmpty() && clientPort.getText().matches("\\d+") &&
                !serverPort.getText().isEmpty() && serverPort.getText().matches("\\d+") &&
                !serverAddress.getText().isEmpty();

        if (condition) {
            joinButton.setDisable(false);
        }
        return condition;
    }

    public void reset() {
        log.setText("");
        serverPort.setText(String.valueOf(ServerConstants.SERVER_DEFAULT_PORT));
        clientPort.setText(String.valueOf(ClientConstants.DEFAULT_CLIENT_PORT));
        clientAddress.setText("todo: client ip");
        gridPane.getChildren().clear();
        gridPane.add(new Text("login"), 0,0);
        gridPane.add(new Text("or"), 1,0);
        gridPane.add(new Text("create new game"), 2,0);
        gridPane.add(loginMenu, 0,1);
        gridPane.add(newGameMenu, 2,1);
        disableLoginOrNewGame(false);
    }

    public void disableLoginOrNewGame(boolean disable) {
        gridPane.setDisable(disable);
    }
}
