package viewers.utils.fancyPopups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.TownHall;
import models.GameLogic.Exceptions.TroopNotFoundException;
import models.GameLogic.Resource;
import models.GameLogic.Village;
import viewers.AppGUI;
import viewers.BattleGroundScene;
import viewers.utils.*;
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.fancyButtons.RoundButton;

import java.util.HashMap;

import static viewers.utils.Const.VIllAGE_BACKGROUND_HEIGHT;
import static viewers.utils.Const.VIllAGE_BACKGROUND_WIDTH;

public class AttackPreview extends GlassPane {

    private Village village;
    private MapBrowserPane draggableView;
    private RoundButton selectTroopsButton;
    private ImageView villageBackground;
    private RoundButton returnButton;

    private ProgressBarItem totalGoldProgressBar;
    private ProgressBarItem totalElixirProgressBar;
    private GridPane totalStock;

    private TroopsScrollMenu troopsScrollMenu;
    private HashMap<String, Integer> selectedTroopsHashMap;
    private RoundButton attackButton;
    private VBox troopPane;
    private Rectangle background;

    public AttackPreview(String title, Village village) {
        super(title);
        this.village = village;
        setProperties();
    }

    @Override
    public void setProperties() {
        villageBackground = new ImageView(ImageLibrary.VillageBackground.getImage());
        background = new Rectangle(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT);
        background.setOpacity(0.8);
        background.setVisible(false);

        draggableView = new MapBrowserPane(villageBackground);
        draggableView.setMaxWidth(VIllAGE_BACKGROUND_WIDTH);
        draggableView.setMaxHeight(VIllAGE_BACKGROUND_HEIGHT);
        draggableView.initialize();
        draggableView.getChildren();

        Resource enemyMapResourceStock = AttackMenuGlassPane.getInstance().getEnemyMapResourceStock(village.getGameMap());
        totalGoldProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_GOLD_INFO, enemyMapResourceStock);
        totalElixirProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_ELIXIR_INFO, enemyMapResourceStock);
        totalStock = new GridPane();
        totalStock.setPadding(new Insets(Const.SPACING));
        totalStock.setAlignment(Pos.CENTER);
        totalStock.setVgap(Const.SPACING);
        totalStock.add(totalGoldProgressBar, 0,0);
        totalStock.add(totalElixirProgressBar, 0,1);

        troopsScrollMenu = AttackMenuGlassPane.getInstance().getTroopsScrollMenu();

        attackButton = new RoundButton("Attack", "green");
        attackButton.setOnAction(event -> {
            selectedTroopsHashMap = AttackMenuGlassPane.getInstance().getSelectedTroopsHashMap();
            System.out.println(selectedTroopsHashMap);
        });

        troopPane = new VBox(Const.SPACING * 3, troopsScrollMenu, attackButton);
        troopPane.setVisible(false);
        troopPane.setAlignment(Pos.CENTER);
        troopPane.setMaxSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT - 100);
        troopPane.setMinSize(Const.WINDOW_WIDTH, Const.WINDOW_HEIGHT - 100);

        selectTroopsButton = new RoundButton("Gather the Army!", "green");
        selectTroopsButton.setOnAction(event -> {
            if (troopPane.isVisible()) {
                troopPane.setVisible(false);
                background.setVisible(false);
            } else {
                troopPane.setVisible(true);
                background.setVisible(true);
            }
        });

        returnButton = new RoundButton("Return", "red");
        returnButton.setOnAction(event -> {
            AppGUI.getMyVillageScene().closeEnemyPreview();
        });

        HBox top = new HBox(title);
        top.setMinSize(Const.WINDOW_WIDTH, 100);
        top.setAlignment(Pos.CENTER);

        HBox bottom = new HBox(Const.SPACING, selectTroopsButton, returnButton);
        bottom.setMinSize(Const.WINDOW_WIDTH, 100);
        bottom.setLayoutY(Const.WINDOW_HEIGHT - 100);
        bottom.setAlignment(Pos.CENTER);

        for (Building building : village.getBuildings()) {
            BuildingHolder buildingHolder = new BuildingHolder(building);
            buildingHolder.refresh();
            int size = (buildingHolder.getEntity().getClass().equals(TownHall.class)) ? 2 : 1;
            IsometricPane.mapToIsometricLayout(buildingHolder, buildingHolder.getEntity().getPosition(), size);
            draggableView.getChildren().add(buildingHolder);
        }

        body.getChildren().addAll(new Pane(draggableView, background, top, bottom, totalStock, troopPane));
    }

    public void refresh() {

    }
}
