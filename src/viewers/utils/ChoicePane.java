package viewers.utils;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ChoicePane extends Pane {
    private VBox mainParts = new VBox();
    private VBox choiceComponents = new VBox();
    private HBox head = new HBox();
    private HBox tail = new HBox();


    public ChoicePane(double minWidth) {
        getChildren().addAll(mainParts);
        setMinWidth(minWidth);
        mainParts.setMinWidth(minWidth);
        mainParts.getChildren().addAll(head, choiceComponents, tail);
    }

    public VBox getChoiceComponents() {
        return choiceComponents;
    }

    public HBox getHead() {
        return head;
    }

    public HBox getTail() {
        return tail;
    }
}
