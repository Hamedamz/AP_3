package viewers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import viewers.utils.DraggablePane;
import viewers.utils.IsometricPane;
import viewers.utils.MapTile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static viewers.utils.Const.*;

public class VillageScene extends Scene{
    private static VillageScene instance = new VillageScene();

    private Group root;
    private DraggablePane draggableView;
    private GridPane tiles;
    private IsometricPane isometricPane;
    private ImageView villageBackground = new ImageView();

    private VillageScene() {
        super(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        root = (Group) getRoot();
        this.getStylesheets().add("/viewers/styles/game.css");
    }

    public static VillageScene getInstance() {
        return instance;
    }

    public Scene build() {
        loadImageView(villageBackground, VIllAGE_BACKGROUND_PATH);

        tiles = new GridPane();
        tiles.setVgap(1);
        tiles.setHgap(1);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                tiles.add(new MapTile(TILE_SIZE, TILE_SIZE), i, j);
            }
        }

        isometricPane = new IsometricPane(tiles);
        draggableView = new DraggablePane(villageBackground, isometricPane);
        draggableView.initialize();
        draggableView.setMaxWidth(VIllAGE_BACKGROUND_WIDTH);
        draggableView.setMaxHeight(VIllAGE_BACKGROUND_HEIGHT);

        root.getChildren().add(draggableView);
        return instance;
    }

    private void loadImageView(ImageView imageView, String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            imageView.setImage(new Image(fileInputStream));
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
