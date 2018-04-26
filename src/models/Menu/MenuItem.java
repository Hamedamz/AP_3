package models.Menu;

import controllers.enums.CommandType;
import controllers.enums.ModelBasedList;

public class MenuItem {
    private CommandType commandType;
    private ModelBasedList modelList;

    public MenuItem(CommandType commandType) {
        this.commandType = commandType;
    }

    public MenuItem(ModelBasedList modelList) {
        this.modelList = modelList;
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
}
