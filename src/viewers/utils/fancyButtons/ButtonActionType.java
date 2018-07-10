package viewers.utils.fancyButtons;

public enum ButtonActionType {
    OPEN_INFO_POPUP("Info"),
    OPEN_UPGRADE_POPUP("Upgrade"),
    OPEN_TRAIN_TROOPS_POPUP("Train Troops"),
    OPEN_BUILD_MENU("Build"),
    OPEN_ATTACK_MENU("Attack"),
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
