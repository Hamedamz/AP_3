package viewers.utils;

public enum ButtonActionType {
    OPEN_INFO_POPUP("Info"),
    OPEN_UPGRADE_POPUP("Upgrade"),
    OPEN_TRAIN_TROOPS_POPUP("Train Troops"),
    OPEN_SHOP_MENU("Shop"),
    OPEN_ATTACK_MENU("Attack");

    private String buttonLabel;

    ButtonActionType(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    @Override
    public String toString() {
        return buttonLabel;
    }
}
