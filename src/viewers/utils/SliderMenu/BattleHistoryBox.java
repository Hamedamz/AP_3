package viewers.utils.SliderMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.AccountInfo;
import models.multiPlayer.leaderBoard.BattleHistory;
import models.multiPlayer.leaderBoard.LeaderBoard;
import viewers.utils.Const;

import java.util.ArrayList;


public class BattleHistoryBox extends Pane {
    public static final double HEIGHT = (Const.WINDOW_HEIGHT - Const.SLIDER_MENU_TAB_HEIGHT);
    private static BattleHistoryBox instance = new BattleHistoryBox();

    public static BattleHistoryBox getInstance() {
        return instance;
    }

    private TableView<BattleHistory> table = new TableView<>();


    public BattleHistoryBox() {
        table.setEditable(true);

        TableColumn battleTimeCol = new TableColumn("Time");
        battleTimeCol.setMinWidth(120);
        battleTimeCol.setCellValueFactory(new PropertyValueFactory<BattleHistory, String>("battleTime"));

        TableColumn attackerNameCol = new TableColumn("Attacker");
        attackerNameCol.setMinWidth(120);
        attackerNameCol.setCellValueFactory(new PropertyValueFactory<BattleHistory, String>("attackerName"));

        TableColumn defenderNameCol = new TableColumn("Defender");
        defenderNameCol.setMinWidth(120);
        defenderNameCol.setCellValueFactory(new PropertyValueFactory<BattleHistory, String>("defenderName"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(120);
        scoreCol.setCellValueFactory(new PropertyValueFactory<BattleHistory, String>("score"));

        TableColumn goldCol = new TableColumn("Gold");
        goldCol.setMinWidth(120);
        goldCol.setCellValueFactory(new PropertyValueFactory<BattleHistory, String>("gold"));

        TableColumn elixirCol = new TableColumn("Elixir");
        elixirCol.setMinWidth(120);
        elixirCol.setCellValueFactory(new PropertyValueFactory<BattleHistory, String>("elixir"));

        table.getColumns().setAll(battleTimeCol, attackerNameCol, defenderNameCol, scoreCol, goldCol, elixirCol);
        table.setMinWidth(Const.SLIDER_MENU_WIDTH * 2);
        table.setMaxHeight(HEIGHT);
        table.setMinHeight(HEIGHT);
        this.getChildren().add(table);
    }

    public void refresh() {
        ArrayList<BattleHistory> history = LeaderBoard.getInstance().getHistory();
        ObservableList<BattleHistory> data = FXCollections.observableArrayList(history);
        table.getItems().clear();
        table.setItems(data);

    }
}
