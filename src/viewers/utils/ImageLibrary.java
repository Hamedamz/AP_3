package viewers.utils;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public enum ImageLibrary {
    VillageBackground("assets/map/Village.jpg"),
    AirDefense("assets/buildings/AirDefense.png"),
    ArcherTower("assets/buildings/ArcherTower.png"),
    Barracks("assets/buildings/Barracks.png"),
    Camp("assets/buildings/Camp.png"),
    Cannon("assets/buildings/Cannon.png"),
    ElixirMine("assets/buildings/ElixirMine.png"),
    ElixirStorage("assets/buildings/ElixirStorage.png"),
    GoldMine("assets/buildings/GoldMine.png"),
    GoldStorage("assets/buildings/GoldStorage.png"),
    TownHall("assets/buildings/TownHall.png"),
    Wall("assets/buildings/Wall.png"),
    WizardTower("assets/buildings/WizardTower.png")
    ;

    private Image image;

    ImageLibrary(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            image = new Image(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage(){
        return image;
    }
}
