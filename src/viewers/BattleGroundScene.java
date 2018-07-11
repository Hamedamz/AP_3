package viewers;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.GameLogic.Bounty;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Position;
import models.GameLogic.Resource;
import viewers.utils.*;
import viewers.utils.entityHolders.BuildingHolder;
import viewers.utils.entityHolders.TroopsHolder;
import viewers.utils.fancyButtons.ButtonActionType;

import java.util.ArrayList;

public class BattleGroundScene extends VillageScene {
    private static BattleGroundScene instance = new BattleGroundScene();

    private Text availableGoldLoot;
    private Text availableElixirLoot;
    private Text acheivedGoldLoot;
    private Text acheivedElixirLoot;
    private GridPane availableLoots;

    private ArrayList<TroopsHolder> troopsHolders;
    private TroopsScrollMenu troopsScrollMenu;
    private GridPane tiles;
    private IsometricPane isometricPane;

    private BattleGroundScene() {
        super();
    }

    public static BattleGroundScene getInstance() {
        return instance;
    }

    public void build() {
        super.build();

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

        draggableView.getChildren().add(isometricPane);

        // building holders
        ArrayList<Building> buildings = new ArrayList<>(AppGUI.getController().getWorld().getBattleGround().getEnemyBuildings());
        addBuildingsFromList(buildings);

        // available loots
        buildAvailableLootsBox();

        // troops
        troopsScrollMenu = new TroopsScrollMenu(ButtonActionType.TROOPS, null);
        troopsScrollMenu.setMaxWidth(Const.POPUP_WIDTH - 6 * Const.SPACING);
        troopsScrollMenu.setLayoutX(Const.WINDOW_WIDTH / 2 - Const.POPUP_WIDTH / 2 + 3 * Const.SPACING);

        root.getChildren().clear();
        root.getChildren().addAll(draggableView, availableLoots, settingsButton, troopsScrollMenu, villageConsole);

        setAnimationTimer().start();
    }

    @Override
    protected AnimationTimer setAnimationTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long now) {
//                refreshAvailableLoots();
                troopsScrollMenu.refreshForBattleGround();

                for (BuildingHolder buildingHolder : buildingHolders) {
                    buildingHolder.refresh();
                }


            }
        };
    }

    private void refreshAvailableLoots() {
        Resource remainingResources = AppGUI.getController().getWorld().getBattleGround().getRemainingResources();
        Bounty lootedBounty = AppGUI.getController().getWorld().getBattleGround().getLootedBounty();
        availableGoldLoot.setText(String.valueOf(remainingResources.getGold()));
        availableElixirLoot.setText(String.valueOf(remainingResources.getElixir()));
        acheivedGoldLoot.setText(String.valueOf(lootedBounty.getGold()));
        acheivedElixirLoot.setText(String.valueOf(lootedBounty.getElixir()));
    }

    private void buildAvailableLootsBox() {
        availableGoldLoot = new Text();
        availableElixirLoot = new Text();
        acheivedGoldLoot = new Text();
        acheivedElixirLoot = new Text();
        availableLoots = new GridPane();
        availableLoots.setAlignment(Pos.CENTER);
        availableLoots.setHgap(Const.SPACING);
        availableLoots.setVgap(Const.SPACING / 2);
        ImageView goldIcon = new ImageView(ImageLibrary.GoldIcon.getImage());
        ImageView elixirIcon = new ImageView(ImageLibrary.ElixirIcon.getImage());
        goldIcon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        goldIcon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);
        elixirIcon.setFitWidth(Const.PROGRESS_BAR_ICON_SIZE);
        elixirIcon.setFitHeight(Const.PROGRESS_BAR_ICON_SIZE);
        availableLoots.add(new Text("available"), 1, 0);
        availableLoots.add(new Text("achieved"), 1, 0);
        availableLoots.add(goldIcon, 0, 1);
        availableLoots.add(availableGoldLoot, 1, 1);
        availableLoots.add(acheivedGoldLoot, 2, 1);
        availableLoots.add(elixirIcon, 0, 2);
        availableLoots.add(availableElixirLoot, 1, 2);
        availableLoots.add(acheivedElixirLoot, 2, 2);
    }

    public void attackListener(Position attacker, Position target) {

    }
}
