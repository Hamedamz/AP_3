package viewers.utils.SliderMenu;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.fancyButtons.RoundButton;

public class SliderMenu extends Pane {
    public static final double WIDTH = Const.WINDOW_WIDTH / 4;
    public static final Duration TRANSITION_DURATION = Duration.millis(500);

    private static SliderMenu instance = new SliderMenu();

    private TabPane tabPane;
    private RoundButton toggleButton;
    private boolean isOpen;

    public static SliderMenu getInstance() {
        return instance;
    }

    private SliderMenu() {
        this.prefHeightProperty().bind(AppGUI.getMainStage().heightProperty());
        this.setMaxWidth(WIDTH);
        this.setPrefWidth(WIDTH);
        isOpen = false;
        this.setLayoutX(-WIDTH);

        toggleButton = new RoundButton(">", "yellow");
        toggleButton.setOnAction(event -> toggleState());

        Tab chatTab = new Tab("Chat");
        chatTab.setContent(ChatBox.getInstance());

        Tab leaderBorad = new Tab();

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMaxHeight(Const.SLIDER_MENU_TAB_HEIGHT);
        tabPane.setTabMinHeight(Const.SLIDER_MENU_TAB_HEIGHT);
        tabPane.getTabs().add(chatTab);
        HBox container = new HBox(Const.SPACING, tabPane, toggleButton);
        container.setAlignment(Pos.CENTER);

        this.getChildren().add(container);
    }

    private void toggleState() {
        Timeline timeline = new Timeline();
        KeyValue keyValue;
        if (isOpen) {
            keyValue = new KeyValue(this.layoutXProperty(), -WIDTH);
        } else {
            keyValue = new KeyValue(this.layoutXProperty(), 0);
        }
        KeyFrame keyFrame = new KeyFrame(TRANSITION_DURATION, keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(toggleButton);
        scaleTransition.setDuration(TRANSITION_DURATION);
        if (isOpen) {
            isOpen = false;
            scaleTransition.setToX(1);
        } else {
            isOpen = true;
            scaleTransition.setToX(-1);
        }
        scaleTransition.play();
    }
}