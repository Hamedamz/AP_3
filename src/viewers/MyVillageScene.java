package viewers;

import controllers.BuildingMenuController;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.ConnectionType;
import models.GameLogic.Builder;
import models.GameLogic.Entities.Buildings.*;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Exceptions.NoSuchAUnderConstructBuildingException;
import models.GameLogic.Position;
import models.GameLogic.Village;
import models.multiPlayer.ConnectionManager;
import models.multiPlayer.InteractionManager;
import viewers.utils.*;
import viewers.utils.SliderMenu.SliderMenu;
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.ButtonActionType;
import viewers.utils.fancyButtons.RoundFancyButton;
import viewers.utils.fancyPopups.AttackMenuGlassPane;
import viewers.utils.fancyPopups.AttackPreview;
import viewers.utils.tiles.MapTile;

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
    private PriceTicket priceTicket;
    private Pane previewPane;
    private Button selectedBuilding;
    private VillageLockPane villageLockPane;
    private ConnectionType connectionType;
    private AnimationTimer animationTimer;

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
        isometricPane.setVisible(false);

        // building holders
        buildingsPane = new Pane();
        ArrayList<Building> buildings = new ArrayList<>(AppGUI.getController().getWorld().getMyVillage().getBuildings());
        addBuildingsFromList(buildings);

        for (Builder builder : AppGUI.getController().getWorld().getMyVillage().getTownHall().getBuilders()) {
            if (builder.isBuilderBusy()) {
                addBuildingToSceneByBuilder(builder, builder.getUnderConstructBuilding().getPosition());
            }
        }

        draggableView.getChildren().addAll(buildingsPane, isometricPane);


        // total stack resources
        totalGoldProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_GOLD_INFO, null);
        totalElixirProgressBar = new ProgressBarItem(ProgressBarType.TOTAL_ELIXIR_INFO, null);
        totalStock = new VBox(Const.SPACING, totalGoldProgressBar, totalElixirProgressBar);
        totalStock.setPadding(new Insets(Const.SPACING));

        // building shop
        shopScrollMenu = ShopScrollMenu.getInstance();
        shopScrollMenu.setVisible(false);
        buildButton = new RoundFancyButton(ButtonActionType.OPEN_BUILD_MENU, "red");
        buildButton.setLayoutX(Const.WINDOW_WIDTH - 80);
        buildButton.setLayoutY(Const.WINDOW_HEIGHT - 100);
        buildButton.setOnMouseClicked(event -> {
            BuildingMenuController.getInstance().hideActiveMenu();
            shopScrollMenu.toggleView();
            AppGUI.getController().getSoundPlayer().play(Sounds.buttonSound);
        });

        // attack button
        attackMenuGlassPane = AttackMenuGlassPane.getInstance();
        attackMenuGlassPane.setProperties();
        attackMenuGlassPane.setVisible(false);
        attackButton = new RoundFancyButton(ButtonActionType.OPEN_ATTACK_MENU, "red");
        attackButton.setLayoutX(Const.SPACING * 3);
        attackButton.setLayoutY(Const.WINDOW_HEIGHT - 100);
        attackButton.setOnMouseClicked(event -> {
            BuildingMenuController.getInstance().hideActiveMenu();
            attackMenuGlassPane.toggleVisibility();
            AppGUI.getController().getSoundPlayer().play(Sounds.buttonSound);
        });

        villageLockPane = new VillageLockPane();
        villageLockPane.setVisible(false);

        animationTimer = setAnimationTimer();
    }

    @Override
    public AnimationTimer setAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (connectionType.equals(ConnectionType.CLIENT) && ConnectionManager.getInstance().getConnectionType().equals(ConnectionType.NONE)) {
                    LogPopup.popError(new Exception("Connection Lost :("));
                    this.stop();
                    AppGUI.setStageScene(GameLobbyScene.getInstance());
                }

                if (InteractionManager.getInstance().isLocked()) {
                    lockVillage();
                } else {
                    unlockVillage();
                }

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
        buildingsPane.getChildren().add(buildingHolder);
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
        Button button = buildingHolder.getButton();
        button.setId("building-image-holder");
        button.setOnMouseEntered(event -> buildingHolder.getGlow().setLevel(0.5));
        button.setOnMouseExited(event -> buildingHolder.getGlow().setLevel(0));
        button.setOnAction(event -> {
            selectedBuilding = button;
            BuildingMenuController.getInstance().handleClickOnBuilding((Building) entity);
            SoundPlayer.play(entity);
        });
        button.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    selectedBuilding = button;
                    BuildingMenuController.getInstance().handleClickOnBuilding((Building) entity);
                    SoundPlayer.play(entity);
                    break;
                case I:
                    selectedBuilding = button;
                    BuildingMenuController.getInstance().setBuilding((Building) entity);
                    BuildingMenuController.getInstance().handleClickOnButton(ButtonActionType.OPEN_INFO_POPUP);
                    break;
                case U:
                    selectedBuilding = button;
                    BuildingMenuController.getInstance().setBuilding((Building) entity);
                    BuildingMenuController.getInstance().handleClickOnButton(ButtonActionType.OPEN_UPGRADE_POPUP);
                    break;

            }
        });
    }

    public void setTileOccupied(int x, int y) {
        MapTile tile = (MapTile) tiles.getChildren().get(x + y * 30);
        tile.setVisible(false);
    }

    public void showTiles(boolean visibility) {
        isometricPane.setVisible(visibility);
    }

    public void reBuild(ConnectionType connectionType) {
        this.connectionType = connectionType;
        root.getChildren().clear();
        root.getChildren().addAll(draggableView, totalStock, buildButton, shopScrollMenu, settingsButton, fastForwardButton);
        if (connectionType.equals(ConnectionType.SINGLE_PLAYER)) {
            root.getChildren().addAll(attackMenuGlassPane, attackButton);
        }
        root.getChildren().addAll(BuildingMenuController.getInstance().getMenus());
        root.getChildren().addAll(villageConsole);
        if (connectionType.equals(ConnectionType.CLIENT)) {
            root.getChildren().addAll(villageLockPane, SliderMenu.getInstance());
        }
        animationTimer.start();
    }

    public void showPriceTicket(String buildingType) {
        priceTicket = new PriceTicket(buildingType);
        root.getChildren().add(priceTicket);
    }

    public void hidePriceTicket() {
        if (priceTicket != null) {
            root.getChildren().remove(priceTicket);
        }
    }

    public void movePriceTicket(double x, double y) {
        if (priceTicket != null) {
            priceTicket.setLayoutX(x);
            priceTicket.setLayoutY(y);
        }
    }

    public void lockVillage() {
        villageLockPane.setVisible(true);
    }

    public void unlockVillage() {
        if (villageLockPane != null && villageLockPane.isVisible()) {
            villageLockPane.setVisible(false);
        }
    }

    public void previewEnemyVillage(Village village, String name) {
        attackMenuGlassPane.setProperties();
        previewPane = new AttackPreview(name, village);
        root.getChildren().add(previewPane);
    }

    public void closeEnemyPreview() {
        root.getChildren().remove(previewPane);
    }

    public void focusOnSelectedBuilding() {
        if (selectedBuilding != null) {
            selectedBuilding.requestFocus();
        }
    }

}
