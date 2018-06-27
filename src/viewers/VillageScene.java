package viewers;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import viewers.utils.DraggablePane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static viewers.utils.Const.*;

public class VillageScene extends Scene{
    private static VillageScene instance = new VillageScene();

    private Group root;
    private DraggablePane mapPane;
    private ImageView mapBackground;

    private VillageScene() {
        super(new Group(), WINDOW_WIDTH, WINDOW_HEIGHT);
        root = (Group) getRoot();
    }

    public static VillageScene getInstance() {
        return instance;
    }

    public Scene build() {
        try {
            mapBackground = new ImageView();
            mapBackground.setImage(new Image(new FileInputStream(new File("assets/map/village.jpg"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mapPane = new DraggablePane(mapBackground);
        mapPane.initialize();
        mapPane.setMaxWidth(VIllAGE_BACKGROUND_WIDTH);
        mapPane.setMaxHeight(VIllAGE_BACKGROUND_HEIGHT);

        root.getChildren().add(mapPane);
        return instance;
    }
}
