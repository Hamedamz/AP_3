package models.Menu;

import controllers.enums.CommandType;
import controllers.enums.ModelBasedList;

public class MenuItem {
    private CommandType commandType;
    private ModelBasedList modelList;
    private boolean isCommand;

    public MenuItem(CommandType commandType) {
        this.commandType = commandType;
        this.isCommand = true;
    }

    public MenuItem(ModelBasedList modelList) {
        this.modelList = modelList;
        this.isCommand = false;
    }

    public String getLabel() {
        return commandType.toString();
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public ModelBasedList getModelList() {
        return modelList;
    }

    public boolean isCommand() {
        return isCommand;
    }
}
