package viewers;

import controllers.BuildingMenuController;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Exceptions.NoSuchAUnderConstructBuildingException;
import models.GameLogic.Position;
import viewers.utils.*;
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.fancyButtons.ButtonActionType;
import viewers.utils.fancyButtons.RoundFancyButton;
import viewers.utils.fancyPopups.AttackMenuGlassPane;

import java.util.ArrayList;

public class MyVillageScene extends VillageScene {
    private static MyVillageScene instance = new MyVillageScene();

    private ProgressBarItem totalGoldProgressBar;
    private ProgressBarItem totalElixirProgressBar;
    private Pane totalStock;
    private RoundFancyButton buildButton;
    private RoundFancyButton attackButton;
    private ShopScrollMenu shopScrollMenu;
    private AttackMenuGlassPane attackMenuGlassPane;
    private GridPane tiles;
    private IsometricPane isometricPane;

    private MyVillageScene() {
        super();
    }

    public static MyVillageScene getInstance() {
        return instance;
    }

    @Override
    public void build() {
        super.build();

        tiles = new GridPane();
        tiles.setVgap(1);
        tiles.setHgap(1);
        for (int j = 0; j < 30; j++) {
            for (int i = 0; i < 30; i++) {
                MapTile mapTile = new MapTile(i, j);
                tiles.add(mapTile, i, j);
                if (AppGUI.getController().getWorld().getMyVillage().getGameMap().isOccupied(i, j)) {
                    mapTile.setVisible(false);
                }
            }
        }
        isometricPane = new IsometricPane(tiles);

        draggableView.getChildren().add(isometricPane);

        // building holders
        ArrayList<Building> buildings = new ArrayList<>(AppGUI.getController().getWorld().getMyVillage().getBuildings());
        addBuildingsFromList(buildings);

        // total stack resources
        totalGoldProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_GOLD_INFO, null);
        totalElixirProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_ELIXIR_INFO, null);
        totalStock = new VBox(Const.SPACING, totalGoldProgressBar, totalElixirProgressBar);
        totalStock.setPadding(new Insets(Const.SPACING));

        // building shop
        shopScrollMenu = new ShopScrollMenu(ButtonActionType.TOWERS);
        shopScrollMenu.setVisible(false);
        buildButton = new RoundFancyButton(ButtonActionType.OPEN_BUILD_MENU, "red");
        buildButton.setLayoutX(Const.WINDOW_WIDTH - 100);
        buildButton.setLayoutY(Const.WINDOW_HEIGHT - 100);
        buildButton.setOnMouseClicked(event -> {
            toggleVisibility(shopScrollMenu);
            SoundPlayer.play(Sounds.buttonSound);
        });

        // attack button
        attackMenuGlassPane = new AttackMenuGlassPane();
        attackMenuGlassPane.setProperties();
        attackMenuGlassPane.setVisible(false);
        attackButton = new RoundFancyButton(ButtonActionType.OPEN_ATTACK_MENU, "red");
        attackButton.setLayoutX(Const.SPACING * 3);
        attackButton.setLayoutY(Const.WINDOW_HEIGHT - 100);
        attackButton.setOnMouseClicked(event -> {
            attackMenuGlassPane.toggleVisibility();
            SoundPlayer.play(Sounds.buttonSound);
        });

        root.getChildren().clear();
        root.getChildren().addAll(draggableView, totalStock, buildButton, shopScrollMenu, settingsButton, attackMenuGlassPane, attackButton, villageConsole);

        setAnimationTimer().start();
    }

    @Override
    public AnimationTimer setAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {

                for (BuildingHolder buildingHolder : buildingHolders) {
                    buildingHolder.refresh();
                }

                totalGoldProgressBar.setValues();
                totalElixirProgressBar.setValues();
            }
        };
    }

    @Override
    public void addBuildingToScene(BuildingHolder buildingHolder) {
        super.addBuildingToScene(buildingHolder);
        setTileOccupied(buildingHolder.getEntity().getPosition().getMapX(), buildingHolder.getEntity().getPosition().getMapY());
        setBuildingHolderEvents(buildingHolder);
    }

    private void addBuildingToSceneByBuilder(Builder builder, Position position) {
        setTileOccupied(position.getMapX(), position.getMapY());
        BuildingHolder buildingHolder = new BuildingHolder(builder);
        IsometricPane.mapToIsometricLayout(buildingHolder, position, 1);
        draggableView.getChildren().add(buildingHolder);
        buildingHolders.add(buildingHolder);
        setBuildingHolderEvents(buildingHolder);
    }

    public void addUnderConstructionBuilding(int x, int y) {
        Builder builder;
        try {
            builder = AppGUI.getController().getWorld().getMyVillage().getTownHall().getBuilder(Position.newMapPosition(x, y));
        } catch (NoSuchAUnderConstructBuildingException e) {
            return;
        }
        addBuildingToSceneByBuilder(builder, Position.newMapPosition(x, y));
    }

    private void setBuildingHolderEvents(BuildingHolder buildingHolder) {
        Entity entity = buildingHolder.getEntity();
        ImageView imageView = buildingHolder.getImageView();
        imageView.setOnMouseEntered(event -> buildingHolder.getGlow().setLevel(0.5));
        imageView.setOnMouseExited(event -> buildingHolder.getGlow().setLevel(0));
        imageView.setOnMouseClicked(event -> {
            if (entity instanceof GoldStorage || entity instanceof GoldMine) {
                SoundPlayer.play(Sounds.goldSound);
            }
            else if (entity instanceof ElixirStorage || entity instanceof ElixirMine) {
                SoundPlayer.play(Sounds.elixirSound);
            }
            else {
                SoundPlayer.play(Sounds.buildingClickSound);
            }
            BuildingMenuController.getInstance().handleClickOnBuilding((Building) entity);
        });
    }

    public void setTileOccupied(int x, int y) {
        MapTile tile = (MapTile) tiles.getChildren().get(x + y * 30);
        tile.setVisible(false);
    }

    public void showBuildButton(double sceneX, double sceneY) {
        buildButton.setLayoutX(sceneX);
        buildButton.setLayoutY(sceneY);
        buildButton.setVisible(true);
    }
}
