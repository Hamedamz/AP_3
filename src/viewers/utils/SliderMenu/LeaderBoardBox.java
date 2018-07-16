package viewers.utils.SliderMenu;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.AccountInfo;
import models.ConnectionManager;
import models.ConnectionType;
import models.GameLogic.BattleGround;
import models.GameLogic.Village;
import models.multiPlayer.Client;
import models.multiPlayer.battleManager.BattleManager;
import models.multiPlayer.leaderBoard.LeaderBoard;
import models.multiPlayer.packet.serverPacket.ServerBattleManagerPacket;
import models.multiPlayer.packet.serverPacket.types.ServerBattleManagerPacketType;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.fancyButtons.RoundButton;

import java.util.ArrayList;
import java.util.Random;

public class LeaderBoardBox extends Pane {
    public static final double RATIO = 0.9;
    public static final double HEIGHT = (Const.WINDOW_HEIGHT - Const.SLIDER_MENU_TAB_HEIGHT);

    private static LeaderBoardBox instance = new LeaderBoardBox();
    public static LeaderBoardBox getInstance() {
        return instance;
    }

    private boolean lock = false;
    private TableView<AccountInfo> table = new TableView<>();
    private RoundButton chooseButton;
    private RoundButton viewButton;
    private RoundButton cancelButton;

    public LeaderBoardBox() {
        table.setEditable(true);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(Const.SLIDER_MENU_WIDTH / 2);
        nameCol.setCellValueFactory(new PropertyValueFactory<AccountInfo, String>("name"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(Const.SLIDER_MENU_WIDTH / 2);
        scoreCol.setCellValueFactory(new PropertyValueFactory<AccountInfo, String>("score"));

        TableColumn goldCol = new TableColumn("Gold");
        goldCol.setMinWidth(Const.SLIDER_MENU_WIDTH / 2);
        goldCol.setCellValueFactory(new PropertyValueFactory<AccountInfo, String>("gold"));

        TableColumn elixirCol = new TableColumn("Elixir");
        elixirCol.setMinWidth(Const.SLIDER_MENU_WIDTH / 2);
        elixirCol.setCellValueFactory(new PropertyValueFactory<AccountInfo, String>("elixir"));

        table.getColumns().setAll(nameCol, scoreCol, goldCol, elixirCol);
        table.setMinWidth(Const.SLIDER_MENU_WIDTH * 2);
        table.setMaxHeight(HEIGHT * RATIO);
        table.setMinHeight(HEIGHT * RATIO);

        chooseButton = new RoundButton("Choose Enemy", "yellow");
        cancelButton = new RoundButton("Cancel", "red");
        cancelButton.setVisible(false);
        viewButton = new RoundButton("View", "green");
        viewButton.setVisible(false);

        chooseButton.setOnAction(event -> {
            lock = true;
            chooseButton.setVisible(false);
            cancelButton.setVisible(true);
            viewButton.setVisible(true);
        });

        cancelButton.setOnAction(event -> {
            lock = false;
            chooseButton.setVisible(true);
            cancelButton.setVisible(false);
            viewButton.setVisible(false);
        });


        viewButton.setOnAction(event -> {
            AccountInfo selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Client.getInstance().sendToServer(new ServerBattleManagerPacket(ServerBattleManagerPacketType.VIEW_S, true, selectedItem.getId()));
                new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        Village village = BattleManager.getInstance().getRequestedVillage();
                        if (village != null) {
                            this.stop();
                            BattleManager.getInstance().setRequestedAccount(selectedItem);
                            AppGUI.getMyVillageScene().previewEnemyVillage(village, selectedItem.getName());
                        }
                    }
                }.start();
            }
        });

        HBox buttons = new HBox(Const.SPACING, chooseButton, viewButton, cancelButton);
        buttons.setMinSize(Const.SLIDER_MENU_WIDTH * 2, HEIGHT * (1 - RATIO));
        buttons.setId("glass-pane");
        buttons.setAlignment(Pos.CENTER);

        VBox leaderBoardBox = new VBox(table);
        if (!ConnectionManager.getInstance().getConnectionType().equals(ConnectionType.SERVER)) {
            leaderBoardBox.getChildren().add(buttons);
        }

        this.getChildren().addAll(leaderBoardBox);
    }

    public void refresh() {
        if (!lock) {
            ArrayList<AccountInfo> leaderBoard = LeaderBoard.getInstance().getLeaderBoard();
            ObservableList<AccountInfo> data = FXCollections.observableArrayList(leaderBoard);
            table.getItems().clear();
            table.setItems(data);
        }
    }

    public static void reset() {
        instance = new LeaderBoardBox();
    }
}
