package viewers.utils;

import javafx.scene.text.Text;

public class StrokeText extends Text {
    public StrokeText(String text) {
        super(text);
        this.setId("stroke-text");
    }

    public StrokeText() {
        this.setId("stroke-text");
    }
}
