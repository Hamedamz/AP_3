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
    LOAD_MAP,
    OVERALL_INFO,
    UPGRADE_INFO,
    CAPACITY_INFO,
    RESOURCES_INFO,
    ATTACK_INFO,
    MAP_INFO,
    ATTACK_MAP,
    BACK,
    OPEN_MENU,
    OPEN_DYNAMIC_MENU;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
