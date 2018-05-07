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
    ATTACK_MAP,
    BACK,
    OPEN_MENU;

    public String toString() {
        return this.name().replace('_', ' ').toLowerCase();
    }
}
