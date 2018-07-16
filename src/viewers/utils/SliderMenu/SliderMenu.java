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
import models.multiPlayer.chatRoom.ChatRoom;
import models.multiPlayer.chatRoom.Message;
import models.multiPlayer.leaderBoard.LeaderBoard;
import viewers.AppGUI;
import viewers.utils.Const;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

import java.util.ArrayList;

public class SliderMenu extends Pane {
    public static final double WIDTH = Const.WINDOW_WIDTH / 4;
    public static final Duration TRANSITION_DURATION = Duration.millis(500);

    private static SliderMenu instance = new SliderMenu();

    private TabPane tabPane;
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
        this.setLayoutX(-WIDTH);

        toggleButton = new RoundButton(">", "yellow");
        toggleButton.setFocusTraversable(false);
        toggleButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            toggleState();
        });

        Tab chatTab = new Tab("Chat");
        chatTab.setContent(ChatBox.getInstance());

        Tab leaderBoardTab = new Tab("Leader Board");
        leaderBoardTab.setContent(LeaderBoardBox.getInstance());

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setTabMaxHeight(Const.SLIDER_MENU_TAB_HEIGHT);
        tabPane.setTabMinHeight(Const.SLIDER_MENU_TAB_HEIGHT);
        tabPane.getTabs().addAll(chatTab, leaderBoardTab);
        HBox container = new HBox(Const.SPACING, tabPane, toggleButton);
        container.setAlignment(Pos.CENTER);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ChatBox.getInstance().refresh();
                LeaderBoardBox.getInstance().refresh();
            }
        };

        this.getChildren().add(container);
    }

    private void toggleState() {
        Timeline timeline = new Timeline();
        KeyValue keyValue;
        if (isOpen) {
            keyValue = new KeyValue(this.layoutXProperty(), -WIDTH, Interpolator.EASE_OUT);
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
}