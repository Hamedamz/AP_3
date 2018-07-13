package viewers.utils.SliderMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import models.multiPlayer.chatRoom.Message;
import viewers.utils.Const;
import viewers.utils.fancyButtons.RoundButton;

public class ChatBox extends Pane {
    public static final double RATIO = 0.9;
    public static final double HEIGHT = (Const.WINDOW_HEIGHT - Const.SLIDER_MENU_TAB_HEIGHT);

    private static ChatBox instance = new ChatBox();

    private TextField textField;
    private RoundButton sendButton;
    private ScrollPane messageScrollPane;
    private VBox messageList;

    public static ChatBox getInstance() {
        return instance;
    }

    private ChatBox() {

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
        messageScrollPane.setMaxHeight(HEIGHT * RATIO);
        messageScrollPane.setPrefHeight(HEIGHT * RATIO);
        messageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScrollPane.setPadding(new Insets(Const.SPACING * 2));
        messageScrollPane.setId("scroll-menu");

        HBox inputBox = new HBox(Const.SPACING, textField, sendButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPrefHeight(HEIGHT * (1 -RATIO));
        VBox chatBox = new VBox(messageScrollPane, inputBox);
        chatBox.setMaxWidth(Const.SLIDER_MENU_WIDTH);
        chatBox.setPrefWidth(Const.SLIDER_MENU_WIDTH);
        this.setId("glass-pane");
        this.getChildren().add(chatBox);
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
        messageScrollPane.layout();
        messageScrollPane.setVvalue(1);
    }

}
