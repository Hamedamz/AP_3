package controllers;

public class InputFormats {
    public static final String NEWGAME_FORMAT = "newGame";
    public static final String LOAD_PATH_FORMAT = "load .+";
    public static final String SAVE_FORMAT = "save .+";
    public static final String TURN_FORMAT = "turn \\d+";
    public static final String SHOW_BUILDINGS_FORMAT = "showBuildings";
    public static final String SHOW_RESOURCES_FORMAT = "resources";
    public static final String INFO_FORMAT = "info";
    public static final String UPGRADE_FORMAT = "upgrade";
    public static final String BACK_FORMAT = "back";
    public static final String SHOW_MENU_FORMAT = "showMenu";
    public static final String WHERE_AM_I_FORMAT = "whereAmI";
    public static final String POSITION_FORMAT = "(\\d+, \\d+)";
    public static final String ATTACK_FORMAT = "attack";
    public static final String LOAD_MAP_FORMAT = "Load map";
    public static final String START_SELECT_FORMAT = "Start select";
    public static final String SELECT_UNIT_FORMAT = "Select \\w+ \\d+";
    public static final String GET_UNIT_STATUS_FORMAT = "status unit \\w+";
    public static final String GET_UNITS_STATUS_FORMAT = "status units";
    public static final String GET_TOWER_STATUS_FORMAT = "status tower \\w+";
    public static final String GET_TOWERS_STATUS_FORMAT = "status towers";
    public static final String GET_ALL_STATUS_FORMAT = "status all";
    public static final String QUIT_ATTACK_FORMAT = "Quit attack";
    public static final String GET_STATUS_RESOURCES_FORMAT = "Status resources";
    public static final String GO_NEXT_TURN_FORMAT = "Go next turn";
    public static final String PUT_TROOP_FORMAT = "Put \\w+ \\d+ in \"\\d+\",\"\\d+\"";
    public static final String END_SELECT_FORMAT = "End select";
}
