package viewers.utils;

public enum ButtonActionType {
    OPEN_INFO_POPUP("Info"),
    OPEN_UPGRADE_POPUP("Upgrade"),
    OPEN_TRAIN_TROOPS_POPUP("Train"),
    OPEN_ARMY_STATUS_POPUP("Army"),
    OPEN_BUILD_MENU("Build"),
    OPEN_ATTACK_MENU("Attack"),
    FAST_FORWARD("Hurry"),
    SETTINGS("Settings"),

    OPEN_PLAY_MENU("Play"),
    OPEN_OPTIONS_MENU("Options"),
    EXIT("Exit"),
    OPEN_SINGLE_PLAYER_MENU("Single Player"),
    OPEN_MULTI_PLAYER_MENU("Multi Player"),
    OPEN_NEW_GAME_MENU("New Game"),
    OPEN_LOAD_GAME_MENU("Load Game"),
    OPEN_HOST_MENU("Host"),
    OPEN_CLIENT_MENU("Cleint"),
    NONE("");

    public static final String[] TROOPS = new String[]{"Archer", "Dragon", "Giant", "Guardian", "Healer", "WallBreaker"};
    public static final String[] TOWERS = new String[]{"AirDefense", "ArcherTower", "Barracks", "Camp", "Cannon",
            "ElixirMine", "ElixirStorage", "GoldMine", "GoldStorage", "GuardianGiant", "Trap", "Wall", "WizardTower"};

    private String buttonLabel;

    ButtonActionType(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    @Override
    public String toString() {
        return buttonLabel;
    }
}
