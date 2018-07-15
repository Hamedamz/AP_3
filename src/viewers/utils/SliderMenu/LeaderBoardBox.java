package viewers.utils.SliderMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.Account;
import models.AccountInfo;
import models.multiPlayer.leaderBoard.LeaderBoard;
import viewers.utils.Const;

import java.util.ArrayList;

public class LeaderBoardBox extends Pane {
    public static final double HEIGHT = (Const.WINDOW_HEIGHT - Const.SLIDER_MENU_TAB_HEIGHT);

    private static LeaderBoardBox instance = new LeaderBoardBox();
    public static LeaderBoardBox getInstance() {
        return instance;
    }

    private TableView<AccountInfo> table = new TableView<>();

    public LeaderBoardBox() {
        table.setEditable(true);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(120);
        nameCol.setCellValueFactory(new PropertyValueFactory<AccountInfo, String>("name"));

        TableColumn idCol = new TableColumn("Id");
        idCol.setMinWidth(80);
        idCol.setCellValueFactory(new PropertyValueFactory<AccountInfo, String>("id"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(80);
        scoreCol.setCellValueFactory(new PropertyValueFactory<AccountInfo, String>("score"));

        table.getColumns().setAll(nameCol, idCol, scoreCol);
        this.getChildren().add(table);
    }

    public void refresh() {
        ArrayList<AccountInfo> leaderBoard = LeaderBoard.getInstance().getLeaderBoard();
        ObservableList<AccountInfo> data = FXCollections.observableArrayList();
        table.getItems().clear();
        table.setItems(data);
    }
}
