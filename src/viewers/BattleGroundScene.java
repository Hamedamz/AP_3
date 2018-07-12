package viewers;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import models.GameLogic.Bounty;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Troop.Troop;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import viewers.utils.*;
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.entityHolders.TroopsHolder;
import viewers.utils.fancyButtons.ButtonActionType;
import viewers.utils.fancyButtons.TroopsFancyButton;
import viewers.utils.fancyPopups.AttackEndGlassPane;

import java.util.ArrayList;
import java.util.Iterator;

public class BattleGroundScene extends VillageScene {
    private static BattleGroundScene instance = new BattleGroundScene();

    private Text leftGoldLoot;
    private Text leftElixirLoot;
    private Text achievedGoldLoot;
    private Text achievedElixirLoot;
    private GridPane lootedBountyInfo;

    private ArrayList<TroopsHolder> troopsHolders = new ArrayList<>();
    private TroopsScrollMenu troopsScrollMenu;
    private GridPane tiles;
    private Pane troopsPane;
    private IsometricPane isometricPane;
    private AttackEndGlassPane attackEndGlassPane;

    private BattleGroundScene() {
        super();
    }

    public static BattleGroundScene getInstance() {
        return instance;
    }

    public void build() {
        super.build();

        // building holders
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

        draggableView.getChildren().addAll(troopsPane, isometricPane);


        // available loots
        lootedBountyInfo = buildLootedBountyInfo();
        lootedBountyInfo.setPadding(new Insets(Const.SPACING));


        // troops
        troopsScrollMenu = new TroopsScrollMenu(ButtonActionType.TROOPS, null);
        troopsScrollMenu.setMaxWidth(Const.POPUP_WIDTH - 6 * Const.SPACING);
        troopsScrollMenu.setLayoutX(Const.WINDOW_WIDTH / 2 - Const.POPUP_WIDTH / 2 + 3 * Const.SPACING);
        troopsScrollMenu.setSelectable(true);

        attackEndGlassPane = new AttackEndGlassPane();
        attackEndGlassPane.setVisible(false);

        root.getChildren().clear();
        root.getChildren().addAll(draggableView, lootedBountyInfo, settingsButton, troopsScrollMenu, attackEndGlassPane, villageConsole);

        setAnimationTimer().start();
    }

    @Override
    protected AnimationTimer setAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
                refreshAvailableLoots();
                troopsScrollMenu.refreshForBattleGround();

                for (BuildingHolder buildingHolder : buildingHolders) {
                    buildingHolder.refresh();
                }

                Iterator<TroopsHolder> iterator = troopsHolders.iterator();
                while (iterator.hasNext()) {
                    TroopsHolder troopsHolder = iterator.next();
                    troopsHolder.refresh();
                    if (troopsHolder.isDestroyed()) {
                        iterator.remove();
                        troopsPane.getChildren().remove(troopsHolder);
                    }
                    IsometricPane.mapToIsometricLayout(troopsHolder, troopsHolder.getEntity().getPosition(), 1);
                }

                if (AppGUI.getController().getWorld().getBattleGround().isGameFinished()) {
                    this.stop();
                    attackEndGlassPane.setProperties();
                    attackEndGlassPane.setVisible(true);
                }

            }
        };
    }

    private void refreshAvailableLoots() {
        Resource remainingResources = AppGUI.getController().getWorld().getBattleGround().getRemainingResources();
        Bounty lootedBounty = AppGUI.getController().getWorld().getBattleGround().getLootedBounty();
        leftGoldLoot.setText(String.valueOf(remainingResources.getGold()));
        leftElixirLoot.setText(String.valueOf(remainingResources.getElixir()));
        achievedGoldLoot.setText(String.valueOf(lootedBounty.getGold()));
        achievedElixirLoot.setText(String.valueOf(lootedBounty.getElixir()));
    }

    private GridPane buildLootedBountyInfo() {
        GridPane gridPane = new GridPane();
        leftGoldLoot = new StrokeText();
        leftElixirLoot = new StrokeText();
        achievedGoldLoot = new StrokeText();
        achievedElixirLoot = new StrokeText();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(Const.SPACING);
        gridPane.setVgap(Const.SPACING / 2);
        ImageView goldIcon = new ImageView(ImageLibrary.GoldIcon.getImage());
        ImageView elixirIcon = new ImageView(ImageLibrary.ElixirIcon.getImage());
        goldIcon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        goldIcon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);
        elixirIcon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        elixirIcon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);
        gridPane.add(new StrokeText("achieved"), 1, 0);
        gridPane.add(new StrokeText("left"), 2, 0);
        gridPane.add(goldIcon, 0, 1);
        gridPane.add(achievedGoldLoot, 1, 1);
        gridPane.add(leftGoldLoot, 2, 1);
        gridPane.add(elixirIcon, 0, 2);
        gridPane.add(achievedElixirLoot, 1, 2);
        gridPane.add(leftElixirLoot, 2, 2);
        return gridPane;
    }

    public void attackListener(Position attacker, Position target) {

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
        TroopsHolder troopsHolder = new TroopsHolder(troop);
        IsometricPane.mapToIsometricLayout(troopsHolder, troop.getPosition(), 1);
        troopsPane.getChildren().add(troopsHolder);
        troopsHolders.add(troopsHolder);
    }
}
