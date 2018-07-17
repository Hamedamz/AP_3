package viewers;

import controllers.JsonHandler;
import controllers.multiPlayer.Client;
import controllers.multiPlayer.packet.serverPacket.ServerInteractionPacket;
import controllers.multiPlayer.packet.serverPacket.types.ServerInteractionPacketType;
import models.GameLogic.Village;
import models.multiPlayer.ConnectionManager;
import models.ConnectionType;
import models.GameLogic.Entities.Buildings.GuardianGiant;
import models.GameLogic.Entities.Entity;
import models.interfaces.Attacker;
import models.interfaces.Destroyable;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.GameLogic.Bounty;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.interfaces.Movable;
import viewers.utils.*;
import viewers.utils.SliderMenu.SliderMenu;
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.entityHolders.TroopHolder;
import viewers.utils.ButtonActionType;
import viewers.utils.fancyButtons.RoundFancyButton;
import viewers.utils.fancyButtons.TroopsFancyButton;
import viewers.utils.fancyPopups.AttackEndGlassPane;
import viewers.utils.fancyPopups.AttackPreview;
import viewers.utils.tiles.HexaTile;

import java.util.ArrayList;
import java.util.Iterator;

public class BattleGroundScene extends VillageScene {
    private static BattleGroundScene instance = new BattleGroundScene();

    private boolean isTurned = false;

    private Text leftGoldLoot;
    private Text leftElixirLoot;
    private Text achievedGoldLoot;
    private Text achievedElixirLoot;
    private Text remainedTime;
    private GridPane battleInfo;

    private ArrayList<TroopHolder> troopHolders = new ArrayList<>();
    private TroopsScrollMenu troopsScrollMenu;
    private GridPane tiles;
    private Pane troopsPane;
    private Pane arrowsPane;
    private IsometricPane isometricPane;
    private RoundFancyButton quitButton;
    private AttackEndGlassPane attackEndGlassPane;
    private AnimationTimer animationTimer;

    private ArrayList<ArrowShot> arrowShots = new ArrayList<>();

    private BattleGroundScene() {
        super();
    }

    public static BattleGroundScene getInstance() {
        return instance;
    }

    public void build() {
        super.build();

        // building holders
        buildingsPane = new Pane();
        ArrayList<Building> buildings = new ArrayList<>(AppGUI.getController().getWorld().getBattleGround().getEnemyBuildings());
        addBuildingsFromList(buildings);

        // tiles
        tiles = new GridPane();
        tiles.setVgap(1);
        tiles.setHgap(1);
        for (int i = 0; i < 29; i++) {
            tiles.add(new HexaTile(0, i), 0, i);
            tiles.add(new HexaTile(29, i + 1), 29, i + 1);
            tiles.add(new HexaTile(i + 1, 0), i + 1, 0);
            tiles.add(new HexaTile(i, 29), i, 29);
        }
        isometricPane = new IsometricPane(tiles);
        troopsPane = new Pane();

        arrowsPane = new Pane();
        draggableView.getChildren().addAll(buildingsPane, troopsPane, arrowsPane, isometricPane);


        // available loots
        battleInfo = buildBattleInfo();
        battleInfo.setPadding(new Insets(Const.SPACING));


        // troops
        troopsScrollMenu = new TroopsScrollMenu(ButtonActionType.TROOPS, null);
        troopsScrollMenu.setMaxWidth(Const.POPUP_WIDTH - 6 * Const.SPACING);
        troopsScrollMenu.setLayoutX(Const.WINDOW_WIDTH / 2 - Const.POPUP_WIDTH / 2 + 3 * Const.SPACING);
        troopsScrollMenu.setSelectable(true);

        // quit button
        quitButton = new RoundFancyButton(ButtonActionType.QUIT_ATTACK, "red");
        quitButton.setLayoutX(Const.WINDOW_WIDTH - 80);
        quitButton.setLayoutY(Const.WINDOW_HEIGHT - 100);
        quitButton.setOnMouseClicked(event -> {
            animationTimer.stop();
            AppGUI.getController().getWorld().getBattleGround().endBattle();
            finishBattle();
        });

        attackEndGlassPane = new AttackEndGlassPane();
        attackEndGlassPane.setVisible(false);

        setRoot();
        animationTimer = setAnimationTimer();
    }

    @Override
    protected AnimationTimer setAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {

                refreshBattleInfo();
                troopsScrollMenu.refreshForBattleGround();

                for (BuildingHolder buildingHolder : new ArrayList<>(buildingHolders)) {
                    buildingHolder.refresh();
                    if (buildingHolder.getEntity().getClass().equals(GuardianGiant.class)) {
                        IsometricPane.mapToIsometricLayout(buildingHolder, buildingHolder.getEntity().getPosition(), 1);
                    }
                }

                showArrows();


                Iterator<TroopHolder> iterator = troopHolders.iterator();
                while (iterator.hasNext()) {
                    TroopHolder troopHolder = iterator.next();
                    troopHolder.refresh();
                    if (troopHolder.isDestroyed()) {
                        iterator.remove();
                        troopsPane.getChildren().remove(troopHolder);
                    }

                    IsometricPane.mapToIsometricLayout(troopHolder, troopHolder.getEntity().getPosition(), 1);
                }

                if (AppGUI.getController().getWorld().getBattleGround().isGameFinished()) {
                    this.stop();
                    finishBattle();
                }
            }
        };
    }

    private void showArrows() {
        Iterator<ArrowShot> iterator = arrowShots.iterator();
        while (iterator.hasNext()) {
            ArrowShot arrow = iterator.next();
            arrowsPane.getChildren().add(arrow);
            iterator.remove();
        }

    }

    private void animateTroopsMovement() {
        ArrayList<TroopHolder> currentTroopsHolders = new ArrayList<>(this.troopHolders);
        for (TroopHolder troopsHolder : currentTroopsHolders) {
            Position nextPosition = ((Troop) troopsHolder.getEntity()).getNextPosition();
            double x = IsometricPane.getIsometricX(nextPosition);
            double y = IsometricPane.getIsometricY(nextPosition);

            if (troopsHolder.getLayoutX() != x && troopsHolder.getLayoutY() != y) {

                Timeline timelineX = new Timeline();
                KeyValue keyValueX = new KeyValue(troopsHolder.layoutXProperty(), x);
                KeyFrame keyFrameX = new KeyFrame(Duration.millis(getGameEngineDuration()), keyValueX);
                timelineX.getKeyFrames().add(keyFrameX);

                Timeline timelineY = new Timeline();
                KeyValue keyValueY = new KeyValue(troopsHolder.layoutYProperty(), y);
                KeyFrame keyFrameY = new KeyFrame(Duration.millis(getGameEngineDuration()), keyValueY);
                timelineY.getKeyFrames().add(keyFrameY);

                timelineX.play();
                timelineY.play();
            }
        }

    }

    private int getGameEngineDuration() {
        return AppGUI.getController().getWorld().getGameEngine().getDuration();
    }

    private void refreshBattleInfo() {
        Resource remainingResources = AppGUI.getController().getWorld().getBattleGround().getRemainingResources();
        Bounty lootedBounty = AppGUI.getController().getWorld().getBattleGround().getLootedBounty();
        int seconds = AppGUI.getController().getWorld().getBattleGround().getTimeRemaining();
        String time = String.format("%02d:%02d", seconds / 60, seconds % 60);
        leftGoldLoot.setText(String.valueOf(remainingResources.getGold()));
        leftElixirLoot.setText(String.valueOf(remainingResources.getElixir()));
        achievedGoldLoot.setText(String.valueOf(lootedBounty.getGold()));
        achievedElixirLoot.setText(String.valueOf(lootedBounty.getElixir()));
        remainedTime.setText(time);
    }

    private GridPane buildBattleInfo() {
        GridPane gridPane = new GridPane();
        leftGoldLoot = new StrokeText();
        leftElixirLoot = new StrokeText();
        achievedGoldLoot = new StrokeText();
        achievedElixirLoot = new StrokeText();
        remainedTime = new StrokeText();
        String name = AppGUI.getController().getWorld().getBattleGround().getEnemyAccountInfo().getName();
        StrokeText defenderName = new StrokeText(name);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(Const.SPACING);
        gridPane.setVgap(Const.SPACING / 2);
        ImageView goldIcon = new ImageView(ImageLibrary.GoldIcon.getImage());
        ImageView elixirIcon = new ImageView(ImageLibrary.ElixirIcon.getImage());
        goldIcon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        goldIcon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);
        elixirIcon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        elixirIcon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);
        gridPane.add(defenderName, 0, 0, 3, 1);
        gridPane.add(new StrokeText("achieved"), 1, 1);
        gridPane.add(new StrokeText("left"), 2, 1);
        gridPane.add(goldIcon, 0, 2);
        gridPane.add(achievedGoldLoot, 1, 2);
        gridPane.add(leftGoldLoot, 2, 2);
        gridPane.add(elixirIcon, 0, 3);
        gridPane.add(achievedElixirLoot, 1, 3);
        gridPane.add(leftElixirLoot, 2, 3);
        gridPane.add(remainedTime, 0, 4, 3, 1);
        return gridPane;
    }

    public String getSelectedTroop() {
        Node selectedItem = troopsScrollMenu.getSelectedItem();
        String type = null;
        if (selectedItem != null) {
            type = ((TroopsFancyButton) selectedItem).getClazz();
        }
        return type;
    }

    public void putTroop(Troop troop) {
        TroopHolder troopHolder = new TroopHolder(troop);
        IsometricPane.mapToIsometricLayout(troopHolder, troop.getPosition(), 1);
        troopsPane.getChildren().add(troopHolder);
        troopHolders.add(troopHolder);
    }

    public void attackHappened(Attacker attacker, Destroyable destroyable) {
        ArrowShot arrowShot = new ArrowShot(attacker, destroyable);
        arrowShots.add(arrowShot);
    }

    public void movementHappened(int direction, Movable movable) {
        if (direction != 0) {
            ((Entity) movable).getImageView().setScaleX(direction);
        }
    }

    public void reBuild() {
        build();
        setRoot();
        animationTimer.start();
    }

    private void setRoot() {
        ConnectionType connectionType = ConnectionManager.getInstance().getConnectionType();
        root.getChildren().clear();
        root.getChildren().addAll(draggableView, battleInfo, settingsButton, quitButton, troopsScrollMenu, attackEndGlassPane, villageConsole);
        if (connectionType.equals(ConnectionType.SERVER) || connectionType.equals(ConnectionType.CLIENT)) {
            root.getChildren().add(SliderMenu.getInstance());
        }

    }

    private void finishBattle() {
        AppGUI.getController().getSoundPlayer().play(Sounds.winSound);
        attackEndGlassPane.setProperties();
        attackEndGlassPane.setVisible(true);
    }

}
