package viewers.utils.riverMenu;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class LoadGameMenu extends StackPane {
    private static LoadGameMenu instance = new LoadGameMenu();

    public static LoadGameMenu getInstance() {
        return instance;
    }

    private ComboBox mapsComboBox;
    private FileChooser fileChooser;
    private RoundButton browseButton;
    private GridPane chooserBox;

    private RoundButton loadButton;
    private TextField password;

    public LoadGameMenu() {
        // file chooser
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("savedMaps"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jSon files", "*.json"));
        chooserBox = new GridPane();
        chooserBox.setHgap(Const.SPACING);
        chooserBox.setAlignment(Pos.CENTER);
        chooserBox.add(mapsComboBox, 0,0);
        chooserBox.add(browseButton, 1,0);

        browseButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(AppGUI.getMainStage());
            if (selectedFiles != null) {
                for (File selectedFile : selectedFiles) {
                    try {
                        AppGUI.getController().loadGameFromFile(selectedFile);
                    } catch (FileNotFoundException e) {
                        AppGUI.getMyVillageScene().handleException(e);
                    }
                }
//                addLoadedMapsToComboBox();
            }
        });

        mapsComboBox.setOnHidden(event ->{
            if (mapsComboBox.getSelectionModel() != null) {
                File mapFile = (File) mapsComboBox.getSelectionModel().getSelectedItem();
//                enemyMap = getGameMap(mapFile);
//                if (enemyMap != null) {
//                    showResources(enemyMap);
//                    if (getSelectedTroopsNumber() != 0){
//                        attackButton.setDisable(false);
//                    }
//                }
            }
        });
    }
}
