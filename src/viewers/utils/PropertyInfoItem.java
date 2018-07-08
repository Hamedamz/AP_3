package viewers.utils;

import interfaces.Upgradable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.GameLogic.Entities.Buildings.Building;
import models.GameLogic.Entities.Buildings.DefensiveBuilding;
import models.GameLogic.Entities.Buildings.Mine;
import models.GameLogic.Entities.Entity;
import models.GameLogic.Entities.Troop.Troop;

public class PropertyInfoItem extends GridPane {
    private PropertyInfoType type;
    private Object model;
    private Label propertyName;
    private Label value;

    public PropertyInfoItem(PropertyInfoType type, Object model) {
        this.type = type;
        this.model = model;
        this.propertyName = new Label();
        this.value = new Label();
        this.add(this.propertyName, 0, 0);
        this.add(this.value, 1, 0);
        setValues();
    }

    private void setValues() {
        propertyName.setText(type.toString() + ": ");
        switch (type) {
            case LEVEL:
                if (model instanceof Building) {
                    value.setText(String.valueOf(((Building) model).getLevel()));
                } else if (model instanceof Troop) {
                    value.setText(String.valueOf(((Troop) model).getLevel()));
                }
                break;
            case TARGET_TYPE:
                value.setText(((DefensiveBuilding) model).getTargetType().toString());
                break;
            case DAMAGE_TYPE:
                value.setText(((DefensiveBuilding) model).getDamageType().toString());
                break;
            case DAMAGE_PER_HIT:
                value.setText(String.valueOf(((DefensiveBuilding) model).getDamage()));
                break;
            case RANGE:
                value.setText(String.valueOf(((DefensiveBuilding) model).getMapRange()));
                break;
            case PRODUCTION_RATE:
                value.setText(String.valueOf(((Mine) model).getProductionRate()));
                break;
        }
    }

    public void setValue(String value) {
        this.value.setText(value);
    }
}

