package controllers.enums;

public enum CommandType {
    NULL,
    NEW_GAME,
    OPEN_BUILDING_MENU,
    UPGRADE_BUILDING,
    BUILD_BUILDING,
    TRAIN_TROOP,
    LOAD_ENEMY_MAP,
    LOAD_GAME_FROM_FILE,
    LOAD_GAME,
    OPEN_MAP_MENU,
    OVERALL_INFO,
    UPGRADE_INFO,
    CAPACITY_INFO,
    RESOURCES_INFO,
    SOURCES_INFO,
    ATTACK_INFO,
    MAP_INFO,
    TARGET,
    ATTACK_MAP,
    BACK,
    OPEN_MENU;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
    public static final String[] TROOPS = new String[]{"Archer", "Dragon", "Giant", "Guardian", "Healer", "WallBreaker"};
    public static final String[] TOWERS = new String[]{"AirDefense", "ArcherTower", "Barracks", "Camp", "Cannon", "ElixirMine", "ElixirStorage", "GoldMine", "GoldStorage", "TownHall", "WizardTower", "GuardianGiant", "Trap", "Wall"};
    public static boolean isTypeValid(String type, String cat) {
        String[] list;
        if (cat.equals("troop")) {
            list = TROOPS;
        } else
            list = TOWERS;
        for (String troop : list) {
            if (type.equals(troop)) {
                return true;
            }
        }
        return false;
    }
}
