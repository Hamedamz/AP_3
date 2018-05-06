package models.Menu;

import controllers.enums.CommandType;

public class MenuItem {
    private String label = null;
    private CommandType commandType;

    public MenuItem(CommandType commandType) {
        this.commandType = commandType;
    }

    public MenuItem(CommandType commandType, String label) {
        this.label = label;
        this.commandType = commandType;
    }

    public String getLabel() {
        if (label == null) {
            return commandType.toString();
        }
        return label;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
