package viewers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import models.GameLogic.Entities.Buildings.Building;
import viewers.utils.DraggablePane;
import viewers.utils.ImageLibrary;
import viewers.utils.IsometricPane;
import viewers.utils.MapTile;

import static viewers.utils.Const.*;

public class VillageScene extends Scene{
    private static VillageScene instance = new VillageScene();

    private Group root;
    private DraggablePane draggableView;
    private GridPane tiles;
    private IsometricPane isometricPane;
    private Pane buildinsPane;
    private ImageView villageBackground = new ImageView();

    private VillageScene() {
        super(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        root = (Group) getRoot();
        this.getStylesheets().add("/viewers/styles/game.css");
        build();
    }

    public static VillageScene getInstance() {
        return instance;
    }

    public Scene build() {
        villageBackground.setImage(ImageLibrary.VillageBackground.getImage());

        tiles = new GridPane();
        tiles.setVgap(1);
        tiles.setHgap(1);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                tiles.add(new MapTile(TILE_SIZE, TILE_SIZE), i, j);
            }
        }
        isometricPane = new IsometricPane(tiles);

        buildinsPane = new Pane();
        for (Building building : AppGUI.getController().getWorld().getMyVillage().getBuildings()) {
            buildinsPane.getChildren().add(building.getImageView());
        }

        draggableView = new DraggablePane(villageBackground, isometricPane);
        draggableView.setMaxWidth(VIllAGE_BACKGROUND_WIDTH);
        draggableView.setMaxHeight(VIllAGE_BACKGROUND_HEIGHT);
        draggableView.initialize();

        root.getChildren().clear();
        root.getChildren().addAll(draggableView);
        return instance;
    }
}
