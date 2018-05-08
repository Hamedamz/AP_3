package controllers;

public class InputFormats {
    public static final String NEWGAME_FORMAT = "newGame";
    public static final String LOAD_PATH_FORMAT = "load .+";
    public static final String SAVE_FORMAT = "save .+";
    public static final String TURN_FORMAT = "turn[\\s]*(\\d+)";
    public static final String SHOW_BUILDINGS_FORMAT = "showBuildings";
    public static final String SHOW_RESOURCES_FORMAT = "resources";
    public static final String INFO_FORMAT = "info";
    public static final String UPGRADE_FORMAT = "upgrade";
    public static final String BACK_FORMAT = "back";
    public static final String SHOW_MENU_FORMAT = "showMenu";
    public static final String WHERE_AM_I_FORMAT = "whereAmI";
    public static final String POSITION_FORMAT = "[(](\\d+),[\\s]*(\\d+)[)]";
    public static final String ATTACK_FORMAT = "attack";
    public static final String LOAD_MAP_FORMAT = "Load map";
    public static final String START_SELECT_FORMAT = "select troops and number of them: (when done enter \"end select\")";
    public static final String SELECT_TROOP_FORMAT = "(\\w+) (\\d+)";
    public static final String STATUS_UNIT_FORMAT = "status unit (\\w+)";
    public static final String STATUS_UNITS_FORMAT = "status units";
    public static final String STATUS_TOWER_FORMAT = "status tower (\\w+)";
    public static final String STATUS_TOWERS_FORMAT = "status towers";
    public static final String STATUS_ALL_FORMAT = "status all";
    public static final String QUIT_ATTACK_FORMAT = "quit attack";
    public static final String STATUS_RESOURCES_FORMAT = "status resource[s]{0,1}";
    public static final String GO_NEXT_TURN_FORMAT = "go next turn";
    public static final String PUT_TROOP_FORMAT = "put (\\w+) (\\d+) in (\\d+),[\\s]*(\\d+)";
    public static final String END_SELECT_FORMAT = "end select";
}
