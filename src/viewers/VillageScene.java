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
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.fancyButtons.ButtonActionType;
import viewers.utils.fancyButtons.RoundFancyButton;

import java.util.ArrayList;

import static viewers.utils.Const.*;

public class VillageScene extends Scene {
    private static VillageScene instance = new VillageScene();

    private Group root;
    private ProgressBarItem totalGoldProgressBar;
    private ProgressBarItem totalElixirProgressBar;
    private Pane totalStock;
    private RoundFancyButton buildButton;
    private ShopScrollMenu shopScrollMenu;
    private ArrayList<BuildingHolder> buildingHolders;
    private GridPane tiles;
    private IsometricPane isometricPane;
    private MapBrowserPane draggableView;
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
        for (int j = 0; j < 30; j++) {
            for (int i = 0; i < 30; i++) {
                MapTile mapTile = new MapTile(TILE_SIZE, TILE_SIZE, i, j);
                tiles.add(mapTile, i, j);
                if (AppGUI.getController().getSinglePlayerWorld().getMyVillage().getGameMap().isOccupied(i, j)) {
                    mapTile.setVisible(false);
                }
            }
        }
        isometricPane = new IsometricPane(tiles);

        draggableView = new MapBrowserPane(villageBackground, isometricPane);
        draggableView.setMaxWidth(VIllAGE_BACKGROUND_WIDTH);
        draggableView.setMaxHeight(VIllAGE_BACKGROUND_HEIGHT);
        draggableView.initialize();

        // building holders
        buildingHolders = new ArrayList<>();
        ArrayList<Building> buildings = new ArrayList<>(AppGUI.getController().getSinglePlayerWorld().getMyVillage().getBuildings());
        buildings.sort((o1, o2) -> o2.getPosition().getMapX() - o1.getPosition().getMapX() + o2.getPosition().getMapY() - o1.getPosition().getMapY());
        for (Building building : buildings) {
            addBuildingToScene(building);
        }

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

        root.getChildren().clear();
        root.getChildren().addAll(draggableView, totalStock, buildButton, shopScrollMenu);

        setAnimationTimer().start();

        //village console
        villageConsole = new VillageConsole();
        root.getChildren().addAll(villageConsole);
        villageConsole.setVillage(AppGUI.getController().getSinglePlayerWorld().getMyVillage());

        //handling total village keyEvents
        addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case BACK_QUOTE:
                    if (villageConsole.isMinimized()) {
                        villageConsole.maximize();
                    } else {
                        villageConsole.minimize();
                    }
                    break;
            }
        });
    }

    private void toggleVisibility(Node node) {
        if (node.isVisible()) {
            node.setVisible(false);
        } else {
            node.setVisible(true);
        }
    }

    private AnimationTimer setAnimationTimer() {
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

    private void addBuildingToScene(Building building) {
        setTileOccupied(building.getPosition().getMapX(), building.getPosition().getMapY());
        BuildingHolder buildingHolder = new BuildingHolder(building);
        int size = (building.getClass().equals(TownHall.class)) ? 2 : 1;
        IsometricPane.mapToIsometricLayout(buildingHolder, building.getPosition(), size);
        draggableView.getChildren().add(buildingHolder);
        buildingHolders.add(buildingHolder);
    }

    private void addBuildingToScene(Builder builder, Position position) {
        setTileOccupied(position.getMapX(), position.getMapY());
        BuildingHolder buildingHolder = new BuildingHolder(builder);
        IsometricPane.mapToIsometricLayout(buildingHolder, position, 1);
        draggableView.getChildren().add(buildingHolder);
        buildingHolders.add(buildingHolder);
    }

    public void addUnderConstructionBuilding(int x, int y) {
        Builder builder;
        try {
            builder = AppGUI.getController().getSinglePlayerWorld().getMyVillage().getTownHall().getBuilder(Position.newMapPosition(x, y));
        } catch (NoSuchAUnderConstructBuildingException e) {
            return;
        }
        addBuildingToScene(builder, Position.newMapPosition(x, y));
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

    public void handleException(Exception e) {
        ErrorPopup.popError(e);
    }
}
