package viewers.utils.SliderMenu;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.AccountInfo;
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
        cancelButton.setScaleX(0);
        viewButton = new RoundButton("View", "green");
        viewButton.setScaleX(0);

        chooseButton.setOnAction(event -> {
            lock = true;
            chooseButton.setScaleX(0);
            cancelButton.setScaleX(1);
            viewButton.setScaleX(1);
        });

        cancelButton.setOnAction(event -> {
            lock = false;
            chooseButton.setScaleX(1);
            cancelButton.setScaleX(0);
            viewButton.setScaleX(0);
        });


        viewButton.setOnAction(event -> {
            AccountInfo selectedItem = table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Client.getInstance().sendToServer(new ServerBattleManagerPacket(ServerBattleManagerPacketType.VIEW_S, true, selectedItem.getId()));
                BattleManager.getInstance().requestedVillageProperty().addListener(new ChangeListener<Village>() {
                    @Override
                    public void changed(ObservableValue<? extends Village> observable, Village oldValue, Village newValue) {
                        Village village = BattleManager.getInstance().getRequestedVillage();
                        if (village != null) {
                            System.out.println("ssss");
                            AppGUI.getMyVillageScene().previewEnemyVillage(village);
                        }
                    }
                });

            }
        });

        HBox hBox = new HBox(Const.SPACING, viewButton, cancelButton, chooseButton);
        hBox.setMinSize(Const.SLIDER_MENU_WIDTH * 2, HEIGHT * (1 - RATIO));
        hBox.setId("glass-pane");

        this.getChildren().addAll(new VBox(table, hBox));
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
