package controllers.enums;

public enum CommandType {
    NEW_GAME,
    LOAD_GAME,
    SAVE_GAME,
    SHOW_BUILDINGS,
    BUILDINGS_LIST,
    SHOW_RESOURCES,
    RESOURCES_LIST,
    UPGRADE_BUILDING,
    INFO,
    BACK,
    OPEN_MENU;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
