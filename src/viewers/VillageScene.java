package viewers;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.TownHall;
import models.GameLogic.Exceptions.NoSuchAUnderConstructBuildingException;
import models.GameLogic.Position;
import viewers.utils.*;

import java.util.ArrayList;

import static viewers.utils.Const.*;

public class VillageScene extends Scene {
    private static VillageScene instance = new VillageScene();

    private Group root;
    private Pane buildingsPane;
    ProgressBarItem totalGoldProgressBar;
    ProgressBarItem totalElixirProgressBar;
    private Pane totalStock;
    private MapBrowserPane draggableView;
    private GridPane tiles;
    private IsometricPane isometricPane;
    private ImageView villageBackground = new ImageView();

    private VillageConsole villageConsole;

    private VillageScene() {
        super(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        root = (Group) getRoot();
        this.getStylesheets().add("/viewers/styles/game.css");
        build();
    }

    public static VillageScene getInstance() {
        return instance;
    }

    public void build() {
        //graphical structure
        villageBackground.setImage(ImageLibrary.VillageBackground.getImage());

        tiles = new GridPane();
        tiles.setVgap(1);
        tiles.setHgap(1);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                tiles.add(new MapTile(TILE_SIZE, TILE_SIZE,i ,j), i, j);
            }
        }
        isometricPane = new IsometricPane(tiles);
        isometricPane.setVisible(false);
        buildingsPane = new Pane();

        draggableView = new MapBrowserPane(villageBackground, buildingsPane, isometricPane);
        draggableView.setMaxWidth(VIllAGE_BACKGROUND_WIDTH);
        draggableView.setMaxHeight(VIllAGE_BACKGROUND_HEIGHT);
        draggableView.initialize();

        ArrayList<Building> buildings = new ArrayList<>(AppGUI.getController().getWorld().getMyVillage().getBuildings());
        buildings.sort((o1, o2) -> o2.getPosition().getMapX() - o1.getPosition().getMapX() + o2.getPosition().getMapY() - o1.getPosition().getMapY());
        for (Building building : buildings) {
            addBuildingToScene(building);
        }
        totalGoldProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_GOLD_INFO, null);
        totalElixirProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_ELIXIR_INFO, null);
        totalStock = new VBox(Const.SPACING, totalGoldProgressBar, totalElixirProgressBar);
        totalStock.setPadding(new Insets(Const.SPACING));

        root.getChildren().clear();
        root.getChildren().addAll(draggableView, totalStock);

        setAnimationTimer().start();

        //village console
        villageConsole = new VillageConsole();
        root.getChildren().addAll(villageConsole);
        villageConsole.setVillage(AppGUI.getController().getWorld().getMyVillage());

        //handling total village keyEvents
        addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case BACK_QUOTE:
                    if(villageConsole.isMinimized()) {
                        villageConsole.maximize();
                    } else {
                        villageConsole.minimize();
                    }
                    break;
            }
        });
    }

    private AnimationTimer setAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Node node : buildingsPane.getChildren()) {
                    BuildingHolder buildingHolder = (BuildingHolder) node;
                    buildingHolder.refresh();
                }

                totalGoldProgressBar.setValues();
                totalElixirProgressBar.setValues();
            }
        };
    }

    private void addBuildingToScene(Building building) {
        BuildingHolder buildingHolder = new BuildingHolder(building);
        int size = (building.getClass().equals(TownHall.class)) ? 2 : 1;
        IsometricPane.mapToIsometricLayout(buildingHolder, building.getPosition(), size);
        buildingsPane.getChildren().add(buildingHolder);
    }

    private void addBuildingToScene(Builder builder, int x, int y) {
        BuildingHolder buildingHolder = new BuildingHolder(builder);
        IsometricPane.mapToIsometricLayout(buildingHolder, new Position(x, y), 1);
        buildingsPane.getChildren().add(buildingHolder);
    }

    public void addUnderConstructionBuilding(int x, int y) {
        Builder builder;
        try {
            builder = AppGUI.getController().getWorld().getMyVillage().getTownHall().getBuilder(Position.newMapPosition(x, y));
        } catch (NoSuchAUnderConstructBuildingException e) {
            return;
        }

        addBuildingToScene(builder, x, y);
    }

    public void setTileOccupied(int x, int y) {
        for (Node node : tiles.getChildren()) {
            MapTile tile = (MapTile) node;
            if (tile.getMapX() == x & tile.getMapY() == y) {
                tile.setVisible(false);
            }
        }
    }
}
