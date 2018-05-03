package models.Menu;

import controllers.enums.CommandType;
import models.GameLogic.Entities.Entity;

public class DynamicMenuItem extends MenuItem {
    private Entity model;

    public DynamicMenuItem(CommandType commandType, Entity model) {
        super(commandType);
        this.model = model;
    }

    public Entity getModel() {
        return model;
    }

    @Override
    public String getLabel() {
        return model.getClass().getSimpleName();
    }
}
