package controllers;

public class InputFormats {
    public static final String LOAD_PATH_FORMAT = "load .+";
    public static final String SAVE_FORMAT = "save .+";
    public static final String TURN_FORMAT = "turn[\\s]*(\\d+)";
    public static final String POSITION_FORMAT = "[(](\\d+),[\\s]?(\\d+)[)]";
    public static final String START_SELECT_FORMAT = "select troops and number of them: (when done enter \"end select\")";
    public static final String SELECT_TROOP_FORMAT = "(\\w+) (\\d+)";
    public static final String STATUS_UNIT_FORMAT = "status unit (\\w+)";
    public static final String STATUS_UNITS_FORMAT = "status units?";
    public static final String STATUS_TOWER_FORMAT = "status tower (\\w+)";
    public static final String STATUS_TOWERS_FORMAT = "status towers?";
    public static final String STATUS_ALL_FORMAT = "status all";
    public static final String QUIT_ATTACK_FORMAT = "quit attack";
    public static final String STATUS_RESOURCES_FORMAT = "status resources?";
    public static final String GO_NEXT_TURN_FORMAT = "go next turn";
    public static final String PUT_TROOP_FORMAT = "put (\\w+) (\\d+) in (\\d+),[\\s]?(\\d+)";
    public static final String END_SELECT_FORMAT = "end select";
}
