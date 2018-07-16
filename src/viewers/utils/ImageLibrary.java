package viewers.utils;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public enum ImageLibrary {
    VillageBackground("assets/map/village.jpg"),
    ZombieMenu("assets/map/ZombieMenu.png"),
    Pumpkins("assets/map/Pumpkins.png"),
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
    Archer("assets/troops/Archer.gif"),
    Dragon("assets/troops/Dragon.png"),
    Giant("assets/troops/Giant.gif"),
    Guardian("assets/troops/Guardian.gif"),
    Healer("assets/troops/Healer.gif"),
    WallBreaker("assets/troops/WallBreaker.gif"),
    InfoIcon("assets/icons/Info.png"),
    UpgradeIcon("assets/icons/Upgrade.png"),
    TrainIcon("assets/icons/Train.png"),
    ArmyIcon("assets/icons/Army.png"),
    BuildIcon("assets/icons/Build.png"),
    SettingsIcon("assets/icons/Settings.png"),
    HurryIcon("assets/icons/Hurry.png"),
    AttackIcon("assets/icons/Attack.png"),
    QuitIcon("assets/icons/Quit.png"),
    HitPointsIcon("assets/icons/HitPointsIcon.png"),
    GoldIcon("assets/icons/GoldIcon.png"),
    ElixirIcon("assets/icons/ElixirIcon.png"),
    TroopsIcon("assets/icons/TroopsIcon.png"),

    LOBBY_0("assets/lobby/bg_0.png"),
    LOBBY_1("assets/lobby/bg_1.png"),
    LOBBY_2("assets/lobby/bg_2.png"),
    LOBBY_3("assets/lobby/bg_3.png"),
    LOBBY_4("assets/lobby/bg_4.png"),
    LOBBY_5("assets/lobby/bg_5.png"),
    LOBBY_6("assets/lobby/bg_6.png"),
    LOBBY_7("assets/lobby/bg_7.png"),
    LOBBY_8("assets/lobby/bg_8.png"),
    LOBBY_9("assets/lobby/bg_9.png"),
    LOBBY_10("assets/lobby/bg_10.png"),
    LOBBY_11("assets/lobby/bg_11.png"),
    LOBBY_12("assets/lobby/bg_12.png");

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
