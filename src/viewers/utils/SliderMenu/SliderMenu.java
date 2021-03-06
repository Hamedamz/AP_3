package viewers.utils.SliderMenu;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.util.Duration;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

public class SliderMenu extends Pane {
    public static final double WIDTH = Const.WINDOW_WIDTH / 4;
    public static final Duration TRANSITION_DURATION = Duration.millis(500);

    private static SliderMenu instance = new SliderMenu();

    private double width = WIDTH;
    private TabPane tabPane;
    private Tab chatTab;
    private Tab leaderBoardTab;
    private Tab battleHistoryTab;
    private RoundButton toggleButton;
    private boolean isOpen;
    private AnimationTimer animationTimer;

    public static SliderMenu getInstance() {
        return instance;
    }

    private SliderMenu() {
        this.prefHeightProperty().bind(AppGUI.getMainStage().heightProperty());
        this.setMaxWidth(WIDTH);
        this.setPrefWidth(WIDTH);
        isOpen = false;
        this.setLayoutX(- WIDTH);

        toggleButton = new RoundButton(">", "yellow");
        toggleButton.setFocusTraversable(false);
        toggleButton.setOnAction(event -> {
            AppGUI.getController().getSoundPlayer().play(Sounds.buttonSound);
            toggleState();
        });

        chatTab = new Tab("Chat");
        chatTab.setContent(ChatBox.getInstance());
        chatTab.setOnSelectionChanged(event -> setWidth(WIDTH));

        leaderBoardTab = new Tab("Leaders");
        leaderBoardTab.setContent(LeaderBoardBox.getInstance());
        leaderBoardTab.setOnSelectionChanged(event -> setWidth(2 * WIDTH));

        battleHistoryTab = new Tab("Battles");
        battleHistoryTab.setContent(BattleHistoryBox.getInstance());
        battleHistoryTab.setOnSelectionChanged(event -> setWidth(2 * WIDTH));

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMaxHeight(Const.SLIDER_MENU_TAB_HEIGHT);
        tabPane.setTabMinHeight(Const.SLIDER_MENU_TAB_HEIGHT);
        tabPane.getTabs().addAll(chatTab, leaderBoardTab, battleHistoryTab);
        HBox container = new HBox(Const.SPACING, tabPane, toggleButton);
        container.setAlignment(Pos.CENTER);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ChatBox.getInstance().refresh();
                LeaderBoardBox.getInstance().refresh();
                BattleHistoryBox.getInstance().refresh();
            }
        };

        this.getChildren().add(container);
    }

    private void toggleState() {
        Timeline timeline = new Timeline();
        KeyValue keyValue;
        if (isOpen) {
            keyValue = new KeyValue(this.layoutXProperty(), - width, Interpolator.EASE_OUT);
        } else {
            keyValue = new KeyValue(this.layoutXProperty(), 0, Interpolator.EASE_OUT);
        }
        KeyFrame keyFrame = new KeyFrame(TRANSITION_DURATION, keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(toggleButton);
        scaleTransition.setDuration(TRANSITION_DURATION);
        if (isOpen) {
            isOpen = false;
            animationTimer.stop();
            scaleTransition.setToX(1);
        } else {
            isOpen = true;
            animationTimer.start();
            scaleTransition.setToX(-1);
        }
        scaleTransition.play();
    }

    @Override
    public void setWidth(double width) {
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        tabPane.setMaxWidth(width);
        tabPane.setMinWidth(width);
        this.width = width;
    }

    public void reset() {
        ChatBox.reset();
        chatTab.setContent(ChatBox.getInstance());
        LeaderBoardBox.reset();
        leaderBoardTab.setContent(LeaderBoardBox.getInstance());
    }
}