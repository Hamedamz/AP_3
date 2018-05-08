package controllers;

public class OutputFormats {

    public static final String OVERALL_INFO_FORMAT = "Level: %d\nHealth: %d";
    public static final String UPGRADE_INFO_FORMAT = "Upgrade info";
    public static final String UPGRADE_COST_FORMAT = "Upgrade Cost: %d";
    public static final String UPGRADE_CONFIRMATION_FORMAT = "Do you want to upgrade %s for %s gold? [Y/N]\n";
    public static final String BUILD_CONFIRMATION_FORMAT = "Do you want to build %s for %s gold and %s elixir? [Y/N]\n";
    public static final String CHOOSE_POSITION_TO_BUILD_FORMAT = "Where do you want to build %s? (x, y)\n";
    public static final String BUILD_SOLDIER_QUANTITY_FORMAT = "How many of this soldier do you want to build?\n";
    public static final String CAMPS_CAPACITY_FORMAT = "Your camps capacity is %d / %d\n";
    public static final String STORAGE_CAPACITY_FORMAT = "Your %s storage is %d / %d loaded.";
    public static final String SHOW_STATUS_RESOURCES_FORMAT = "gold achieved: %d\nelixir achieves: %d\ngold remained in map: %d\nelixir remained in map: %d\n";
    public static final String SHOW_STATUS_UNIT_FORMAT = "%s level = %d in (%d,%d) with health = %d\n";
    public static final String SHOW_STATUS_TOWER_FORMAT = "%s level = %d in (%d,%d) with health = %d\n";
    public static final String ATTACK_FINISHED_INFO_FORMAT = "the war ended with %d golds, %d elixirs and %d scores achieved!\n";
}
