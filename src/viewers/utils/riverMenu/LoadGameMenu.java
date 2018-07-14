package viewers.utils.riverMenu;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import viewers.AppGUI;
import viewers.MyVillageScene;
import viewers.utils.Const;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        browseButton = new RoundButton("Browse", "yellow");
        browseButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(AppGUI.getMainStage());
            if (selectedFiles != null) {
                for (File selectedFile : selectedFiles) {
                    try {
                        // TODO: 7/14/2018
                        AppGUI.getController().loadGameFromFile(selectedFile);
                    } catch (FileNotFoundException e) {
                        AppGUI.getMyVillageScene().handleException(e);
                    }
                }
                addLoadedMapsToComboBox();
            }
        });

        loadButton = new RoundButton("Load Game", "green");
        loadButton.setDisable(true);
        loadButton.setOnAction(event -> {
            // TODO: 7/14/2018 edit controller
            File selectedMap = (File) mapsComboBox.getSelectionModel().getSelectedItem();
            MyVillageScene.getInstance().reBuild();
            AppGUI.setStageScene(MyVillageScene.getInstance());
            SoundPlayer.play(Sounds.loadSound);
            SoundPlayer.playBackground(Sounds.mainSound);
        });

        mapsComboBox = new ComboBox();
        mapsComboBox.setPrefWidth(Const.RIVER_MENU_SIZE);
        mapsComboBox.setMaxWidth(Const.RIVER_MENU_SIZE);
        mapsComboBox.setOnHidden(event -> {
            if (mapsComboBox.getSelectionModel() != null) {
                loadButton.setDisable(false);
            }
        });

        this.setPrefSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        this.setMaxSize(Const.RIVER_MENU_SIZE * 2, Const.WINDOW_HEIGHT);
        GridPane gridPane = new GridPane();
        gridPane.add(mapsComboBox, 0,0);
        gridPane.add(browseButton, 1,0);
        gridPane.add(loadButton, 0,1);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(Const.SPACING);
        gridPane.setHgap(Const.SPACING);

        this.getChildren().addAll(gridPane);
    }

    private void addLoadedMapsToComboBox() {
        // TODO: 7/14/2018
//        Set<Map.Entry<String, File>> entries = AppGUI.getController().getWorld().getMyVillagesNameAndFile().entrySet();

    }
}
