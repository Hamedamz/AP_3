package viewers.utils;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PropertyInfoItem extends GridPane {
    private Label propertyName;
    private Label value;

    public PropertyInfoItem(String propertyName, String value) {
        this.propertyName = new Label(propertyName + ": ");
        this.value = new Label(value);
        this.add(this.propertyName, 0, 0);
        this.add(this.value, 1, 0);
    }

    public void setValue(String value) {
        this.value.setText(value);
    }
}

