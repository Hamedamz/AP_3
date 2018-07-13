package viewers.utils;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public enum ImageLibrary {
    VillageBackground("assets/map/village.jpg"),
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
    WizardTower("assets/buildings/WizardTower.png"),
    GuardianGiant("assets/buildings/GuardianGiant.png"),
    Trap("assets/buildings/Trap.png"),
    Archer("assets/troops/Archer.png"),
    Dragon("assets/troops/Dragon.png"),
    Giant("assets/troops/Giant.png"),
    Guardian("assets/troops/Guardian.png"),
    Healer("assets/troops/Healer.png"),
    WallBreaker("assets/troops/WallBreaker.png"),
    InfoIcon("assets/icons/Info.png"),
    UpgradeIcon("assets/icons/Upgrade.png"),
    TrainIcon("assets/icons/Train.png"),
    ArmyIcon("assets/icons/Army.png"),
    BuildIcon("assets/icons/Build.png"),
    SettingsIcon("assets/icons/Settings.png"),
    HurryIcon("assets/icons/Hurry.png"),
    AttackIcon("assets/icons/Attack.png"),
    HitPointsIcon("assets/icons/HitPointsIcon.png"),
    GoldIcon("assets/icons/GoldIcon.png"),
    ElixirIcon("assets/icons/ElixirIcon.png"),
    TroopsIcon("assets/icons/TroopsIcon.png");

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
