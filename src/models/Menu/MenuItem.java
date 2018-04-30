package models.Menu;

import controllers.enums.CommandType;

public class MenuItem {
    private CommandType commandType;

    public MenuItem(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getLabel() {
        return commandType.toString();
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
