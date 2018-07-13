package viewers.utils.fancyPopups;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.Storage;
import models.GameLogic.Exceptions.TroopNotFoundException;
import models.GameLogic.GameMap;
import models.GameLogic.Resource;
import viewers.AppGUI;
import viewers.BattleGroundScene;
import viewers.utils.*;
import viewers.utils.fancyButtons.ButtonActionType;
import viewers.utils.fancyButtons.RoundButton;
import viewers.utils.fancyButtons.TroopsFancyButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttackMenuGlassPane extends GlassPane {

    private GameMap enemyMap;

    private FileChooser fileChooser;
    private ComboBox mapsComboBox;
    private RoundButton browseButton;
    private GridPane chooserBox;

    private ProgressBarItem totalGoldProgressBar;
    private ProgressBarItem totalElixirProgressBar;
    private GridPane totalStock;

    private TroopsScrollMenu troopsScrollMenu;
    private HashMap<String, Integer> troopsMaxNumberHashMap;
    private HashMap<String, Integer> selectedTroopsHashMap;
    private RoundButton attackButton;

    public AttackMenuGlassPane() {
        super("Select an Enemy to attack!");
    }

    @Override
    public void setProperties() {
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
        totalStock = new GridPane();
        totalStock.setAlignment(Pos.CENTER);
        totalStock.setVgap(Const.SPACING);
        totalStock.setMinHeight(150);

        // troops
        troopsScrollMenu = new TroopsScrollMenu(ButtonActionType.TROOPS, null);
        troopsScrollMenu.setMaxWidth(Const.POPUP_WIDTH - 6 * Const.SPACING);
        refreshTroops();
        setIncrementButtonsEvents();

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
                enemyMap = getGameMap(mapFile);
                if (enemyMap != null) {
                    showResources(enemyMap);
                    if (getSelectedTroopsNumber() != 0){
                        attackButton.setDisable(false);
                    }
                }
            }
        });

        attackButton.setOnAction(event -> {
            AppGUI.getController().setEnemyMap(enemyMap);
            try {
                AppGUI.getController().setSelectedTroops(selectedTroopsHashMap);
            } catch (TroopNotFoundException e) {
                AppGUI.getMyVillageScene().handleException(e);
            }
            BattleGroundScene.getInstance().reBuild();
            AppGUI.setStageScene(BattleGroundScene.getInstance());
            SoundPlayer.play(Sounds.warSound);
            this.toggleVisibility();
        });

        body.getChildren().clear();
        body.getChildren().addAll(title, chooserBox, totalStock, troopsScrollMenu, attackButton);
    }

    private void setIncrementButtonsEvents() {
        for (Node node : troopsScrollMenu.getButtons().getChildren()) {

            TroopsFancyButton fancyButton = (TroopsFancyButton) node;

            fancyButton.getIncrementButton().setOnAction(event -> {
                if (!fancyButton.getIncrementButton().isDisable()) {
                    SoundPlayer.play(Sounds.buttonSound);
                }
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
                if (!fancyButton.getDecrementButton().isDisable()) {
                    SoundPlayer.play(Sounds.buttonSound);
                }
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
        totalStock.add(totalGoldProgressBar, 0,0);
        totalStock.add(totalElixirProgressBar, 0,1);
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

    private int getSelectedTroopsNumber() {
        int number = 0;
        for (Map.Entry<String, Integer> troop : selectedTroopsHashMap.entrySet()) {
            number += troop.getValue();
        }
        return number;
    }
}
