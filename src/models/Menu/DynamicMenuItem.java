package models.Menu;

import controllers.enums.CommandType;
import models.GameLogic.Entities.Entity;

public class DynamicMenuItem extends MenuItem {
    private Entity model;

    public DynamicMenuItem(CommandType commandType, Entity model) {
        super(commandType);
        this.model = model;
    }

    public DynamicMenuItem(CommandType commandType, String label) {
        super(commandType, label);
    }

    public Entity getModel() {
        return model;
    }

    @Override
    public String getLabel() {
        if (model != null) {
            return model.getClass().getSimpleName();
        }
        return super.getLabel();
    }
}
