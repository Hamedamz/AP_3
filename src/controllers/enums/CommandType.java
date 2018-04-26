package controllers.enums;

public enum CommandType {
    NEW_GAME,
    LOAD_GAME,
    SAVE_GAME,
    SHOW_BUILDINGS,
    SHOW_RESOURCES,
    UPGRADE_BUILDING,
    AVAILABLE_BUILDINGS,
    STATUS,
    OVERAL_INFO,
    UPGRADE_INFO,
    BACK,
    OPEN_MENU;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
