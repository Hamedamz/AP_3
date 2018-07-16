package viewers;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Village;

import java.util.ArrayList;

public class VillagePreviewScene extends VillageScene {
    public static Pane getPreview(Village village) {
        return new VillagePreviewScene(village).draggableView;
    }

    private Village village;

    private VillagePreviewScene(Village village) {
        super();
        this.village = village;
    }

    @Override
    public void build() {
        super.build();

        // building holders
        buildingsPane = new Pane();
        ArrayList<Building> buildings = new ArrayList<>(village.getBuildings());
        addBuildingsFromList(buildings);

        draggableView.getChildren().add(buildingsPane);

        reBuild();
    }

    @Override
    protected AnimationTimer setAnimationTimer() {
        return null;
    }

    @Override
    public void reBuild() {

    }
}
