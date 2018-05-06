package controllers.enums;

public enum CommandType {
    NEW_GAME,
    LOAD_GAME,
    OPEN_BUILDING_MENU,
    UPGRADE_BUILDING,
    BUILD_BUILDING,
    LOAD_MAP,
    OVERALL_INFO,
    UPGRADE_INFO,
    CAPACITY_INFO,
    RESOURCES_INFO,
    SOURCES_INFO,
    ATTACK_INFO,
    MAP_INFO,
    ATTACK_MAP,
    BACK,
    OPEN_MENU;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
