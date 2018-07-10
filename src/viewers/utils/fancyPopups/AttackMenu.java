package viewers.utils.fancyPopups;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Storage;
import models.GameLogic.GameMap;
import models.GameLogic.Resource;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.ProgressBarItem;
import viewers.utils.ProgressBarType;
import viewers.utils.TroopsScrollMenu;
import viewers.utils.fancyButtons.ButtonActionType;
import viewers.utils.fancyButtons.RoundButton;
import viewers.utils.fancyButtons.TroopsFancyButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttackMenu extends StackPane {

    private GameMap gameMap;

    private FileChooser fileChooser;
    private ComboBox mapsComboBox;
    private RoundButton browseButton;
    private HBox chooserBox;

    private ProgressBarItem totalGoldProgressBar;
    private ProgressBarItem totalElixirProgressBar;
    private VBox totalStock;

    private TroopsScrollMenu troopsScrollMenu;
    private HashMap<String, Integer> troopsMaxNumberHashMap;
    private HashMap<String, Integer> selectedTroopsHashMap;
    private RoundButton attackButton;

    private VBox body;
    private Rectangle background;

    public AttackMenu() {
        setProperties();
    }

    private void setProperties() {
        this.setHeight(Const.WINDOW_HEIGHT);
        this.setWidth(Const.WINDOW_WIDTH);
        background = new Rectangle(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT, Color.BLACK);
        background.setOpacity(0.75);

        // buttons
        browseButton = new RoundButton("Browse", "yellow");
        attackButton = new RoundButton("attack", "red");
        attackButton.setDisable(true);

        // combo box
        mapsComboBox = new ComboBox();
        mapsComboBox.setMaxWidth(Const.POPUP_WIDTH - 10 * Const.SPACING);
        mapsComboBox.setPrefWidth(Const.POPUP_WIDTH - 10 * Const.SPACING);
        addLoadedEnemyMaps();

        // total stock
        totalStock = new VBox(Const.SPACING);
        totalStock.setMinHeight(150);
        totalStock.setTranslateX(Const.WINDOW_WIDTH / 2 - 8 * Const.SPACING);

        // troops
        troopsScrollMenu = new TroopsScrollMenu(ButtonActionType.TROOPS, null);
        troopsScrollMenu.setMaxWidth(Const.POPUP_WIDTH - 6 * Const.SPACING);
        refreshTroops();
        setIncrementButtonsEvents();

        // file chooser
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("savedMaps"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jSon files", "*.json"));
        chooserBox = new HBox(Const.SPACING, mapsComboBox, browseButton);
        chooserBox.setTranslateX(Const.WINDOW_WIDTH / 2 - Const.POPUP_WIDTH / 2);


        browseButton.setOnAction(event -> {
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(AppGUI.getMainStage());
            if (selectedFiles != null) {
                for (File selectedFile : selectedFiles) {
                    try {
                        AppGUI.getController().loadEnemyMap(selectedFile);
                    } catch (FileNotFoundException e) {
                        AppGUI.getMyVillageScene().handleException(e);
                    }
                }
                addLoadedEnemyMaps();
            }
        });

        mapsComboBox.setOnHidden(event ->{
            if (mapsComboBox.getSelectionModel() != null) {
                File mapFile = (File) mapsComboBox.getSelectionModel().getSelectedItem();
                gameMap = getGameMap(mapFile);
                if (gameMap != null) {
                    showResources(gameMap);
                    attackButton.setDisable(false);
                }
            }
        });

        body = new VBox(Const.SPACING * 3, chooserBox, totalStock, troopsScrollMenu, attackButton);
        body.setAlignment(Pos.CENTER);
        body.setMinWidth(Const.POPUP_WIDTH);
        body.setMinHeight(Const.WINDOW_HEIGHT);
        body.setLayoutX(Const.WINDOW_WIDTH / 2 - Const.POPUP_WIDTH / 2);
        this.getChildren().addAll(background, body);
    }

    private void setIncrementButtonsEvents() {
        for (Node node : troopsScrollMenu.getButtons().getChildren()) {

            TroopsFancyButton fancyButton = (TroopsFancyButton) node;

            fancyButton.getIncrementButton().setOnAction(event -> {
                int newValue = selectedTroopsHashMap.get(fancyButton.getClazz()) + 1;
                if (newValue <= troopsMaxNumberHashMap.get(fancyButton.getClazz())) {
                    selectedTroopsHashMap.put(fancyButton.getClazz(), newValue);
                    fancyButton.setNumberBadge(newValue);
                    if (newValue == troopsMaxNumberHashMap.get(fancyButton.getClazz())) {
                        fancyButton.getIncrementButton().setDisable(true);
                    }
                    fancyButton.getDecrementButton().setDisable(false);
                }
            });

            fancyButton.getDecrementButton().setOnAction(event -> {
                int newValue = selectedTroopsHashMap.get(fancyButton.getClazz()) - 1;
                if (newValue >= 0) {
                    selectedTroopsHashMap.put(fancyButton.getClazz(), newValue);
                    fancyButton.setNumberBadge(newValue);
                    if (newValue == 0) {
                        fancyButton.getDecrementButton().setDisable(true);
                    }
                    fancyButton.getIncrementButton().setDisable(false);
                }
            });
        }
    }

    private void refreshIncrementButtons() {
        for (Node node : troopsScrollMenu.getButtons().getChildren()) {
            TroopsFancyButton fancyButton = (TroopsFancyButton) node;

            fancyButton.getIncrementButton().setDisable(true);
            if (troopsMaxNumberHashMap.get(fancyButton.getClazz()) == 0) {
                fancyButton.getDecrementButton().setDisable(true);
            } else {
                fancyButton.getDecrementButton().setDisable(false);
            }
        }
    }

    private void showResources(GameMap gameMap) {
        Resource enemyMapResourceStock = getEnemyMapResourceStock(gameMap);
        totalGoldProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_GOLD_INFO, enemyMapResourceStock);
        totalElixirProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_ELIXIR_INFO, enemyMapResourceStock);
        totalStock.getChildren().clear();
        totalStock.getChildren().addAll(totalGoldProgressBar, totalElixirProgressBar);
    }

    private void addLoadedEnemyMaps() {
        mapsComboBox.getItems().clear();
        Set<Map.Entry<File, GameMap>> entries = AppGUI.getController().getWorld().getEnemyVillagesFileAndMap().entrySet();
        for (Map.Entry<File, GameMap> entry : entries) {
            mapsComboBox.getItems().add(entry.getKey());
        }
    }

    private GameMap getGameMap(File file) {
        return AppGUI.getController().getWorld().getEnemyVillagesFileAndMap().get(file);
    }

    private Resource getEnemyMapResourceStock(GameMap gameMap) {
        Resource resource = new Resource(0, 0);
        for (Building building : gameMap.getBuildings()) {
            if (building instanceof Storage) {
                Storage storage = (Storage) building;
                resource.addToThisResource(storage.getStock());
            }
        }
        return resource;
    }

    public void toggleVisibility() {
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            refreshTroops();
            this.setVisible(true);
        }
    }

    private void refreshTroops() {
        troopsScrollMenu.buildForAttackMenu();
        troopsMaxNumberHashMap = troopsScrollMenu.refreshForAttackMenu();
        selectedTroopsHashMap = new HashMap<>(troopsMaxNumberHashMap);
        refreshIncrementButtons();
    }
}