package viewers.utils.chatRoom;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import models.multiPlayer.chatRoom.Message;
import viewers.utils.Const;
import viewers.utils.fancyButtons.RoundButton;

public class ChatBox extends Pane {
    public static final double WIDTH = Const.WINDOW_WIDTH / 4;
    public static final double RATIO = 0.9;
    public static final Duration TRANSITION_DURATION = Duration.millis(500);
    private static ChatBox instance = new ChatBox();

    private TextField textField;
    private RoundButton sendButton;
    private ScrollPane messageScrollPane;
    private VBox messageList;
    private RoundButton toggleButton;
    private boolean isOpen;

    public static ChatBox getInstance() {
        return instance;
    }

    private ChatBox() {
        isOpen = false;
        this.setLayoutX(-WIDTH);

        toggleButton = new RoundButton(">", "yellow");
        toggleButton.setOnAction(event -> {
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

            toggleState();
        });

        textField = new TextField();
        textField.setPromptText("Message");
        textField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    sendMessage();
                break;
        }});

        sendButton = new RoundButton("send", "green");
        sendButton.setOnAction(event -> sendMessage());

        messageList = new VBox(Const.SPACING);
        messageScrollPane = new ScrollPane(messageList);
        messageScrollPane.setMaxHeight(Const.WINDOW_HEIGHT * RATIO);
        messageScrollPane.setPrefHeight(Const.WINDOW_HEIGHT * RATIO);
        messageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScrollPane.setPadding(new Insets(Const.SPACING * 2));
        messageScrollPane.setId("scroll-menu");

        HBox inputBox = new HBox(Const.SPACING, textField, sendButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPrefHeight(Const.WINDOW_HEIGHT * (1 -RATIO));
        VBox chatBox = new VBox(messageScrollPane, inputBox);
        chatBox.setMaxWidth(WIDTH);
        chatBox.setPrefWidth(WIDTH);
        chatBox.setId("chat-box");
        HBox container = new HBox(Const.SPACING, chatBox, toggleButton);
        container.setAlignment(Pos.CENTER);

        this.getChildren().add(container);
    }

    private void toggleState() {
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

    public void recieveMessage(models.multiPlayer.chatRoom.Message message) {
        // TODO: 7/13/2018
        MessageBubble messageBubble = new MessageBubble(message);
        appendMessage(messageBubble);
    }

    private void sendMessage() {
        String text = textField.getText();
        if (text != null && !text.equals("")) {
            // TODO: 7/13/2018 creating Message
            Message message = new Message(text, "FROM", "TO");

            // TODO: 7/13/2018 send to server

            // add to ui
            appendMessage(new MessageBubble(message));
        }
    }

    private void appendMessage(MessageBubble messageBubble) {
        textField.setText("");
        messageList.getChildren().add(messageBubble);
        messageScrollPane.setVvalue(1);
    }

}
