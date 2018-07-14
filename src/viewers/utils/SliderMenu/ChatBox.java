package viewers.utils.SliderMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import models.ConnectionManager;
import models.multiPlayer.Client;
import models.multiPlayer.Server;
import models.multiPlayer.chatRoom.ChatRoom;
import models.multiPlayer.chatRoom.Message;
import models.multiPlayer.packet.clientPacket.ClientChatPacket;
import models.multiPlayer.packet.serverPacket.ServerChatPacket;
import models.multiPlayer.runnables.PacketListener;
import viewers.utils.Const;
import viewers.utils.SoundPlayer;
import viewers.utils.Sounds;
import viewers.utils.fancyButtons.RoundButton;

import java.util.List;

import static models.ConnectionType.CLIENT;
import static models.ConnectionType.SERVER;
import static models.multiPlayer.packet.serverPacket.ServerChatPacketType.SEND;

public class ChatBox extends Pane implements PacketListener<ClientChatPacket> {
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
        sendButton.setOnAction(event -> {
            SoundPlayer.play(Sounds.buttonSound);
            sendMessage();
        });

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

    public void receiveMessage(models.multiPlayer.chatRoom.Message message) {
        MessageBubble messageBubble = new MessageBubble(message);
        appendMessage(messageBubble);
    }

    private synchronized void sendMessage() {
        String text = textField.getText();
        if (text != null && !text.equals("")) {
            if(ConnectionManager.getInstance().getConnectionType().equals(CLIENT)) {
                Message message = new Message(text, Client.getInstance().getAccount().getUserName());
                Client.getInstance().sendToServer(new ServerChatPacket(SEND, message));
            } else if (ConnectionManager.getInstance().getConnectionType().equals(SERVER)){
                Message message = new Message(text, "@SERVER@");
                ChatRoom.getInstance().receive(new ServerChatPacket(SEND, message));
            }
        }
    }

    private void appendMessage(MessageBubble messageBubble) {
        textField.setText("");
        messageList.getChildren().add(messageBubble);
        messageScrollPane.layout();
        messageScrollPane.setVvalue(1);
    }



    @Override
    public synchronized void receive(ClientChatPacket clientChatPacket) {
        switch (clientChatPacket.getClientChatPacketType()) {
            case LAST_MESSAGE:
                receiveMessage((Message) clientChatPacket.getElements()[0]);
                break;
            case RECENT_MESSAGES:
                messageList.getChildren().clear();
                for(Message message : (List<Message>) clientChatPacket.getElements()[0]) {
                    receiveMessage(message);
                }
        }
    }
}
