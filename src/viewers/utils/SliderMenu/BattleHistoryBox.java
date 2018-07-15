package viewers.utils.SliderMenu;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;


public class BattleHistoryBox extends Pane {
    private BattleHistoryBox instance = new BattleHistoryBox();

    public BattleHistoryBox getInstance() {
        return instance;
    }

//    private TableView<TYPE> table = new TableView<>();


    public BattleHistoryBox() {
//        table.setEditable(true);

        TableColumn battleTimeCol = new TableColumn("Time");
        battleTimeCol.setMinWidth(120);
//        battleTimeCol.setCellValueFactory(new PropertyValueFactory<TYPE, String>("battleTime"));

        TableColumn attackerNameCol = new TableColumn("Attacker");
        attackerNameCol.setMinWidth(120);
//        attackerNameCol.setCellValueFactory(new PropertyValueFactory<TYPE, String>("attackerName"));

        TableColumn defenderNameCol = new TableColumn("Defender");
        defenderNameCol.setMinWidth(120);
//        defenderNameCol.setCellValueFactory(new PropertyValueFactory<TYPE, String>("defenderName"));

        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setMinWidth(120);
//        scoreCol.setCellValueFactory(new PropertyValueFactory<TYPE, String>("score"));

        TableColumn goldCol = new TableColumn("Gold");
        goldCol.setMinWidth(120);
//        goldCol.setCellValueFactory(new PropertyValueFactory<TYPE, String>("gold"));

        TableColumn elixirCol = new TableColumn("Elixir");
        elixirCol.setMinWidth(120);
//        elixirCol.setCellValueFactory(new PropertyValueFactory<TYPE, String>("elixir"));

//        table.getColumns().setAll(battleTimeCol, attackerNameCol, defenderNameCol, scoreCol, goldCol, elixirCol);
//        this.getChildren().add(table);
    }
}
