package models.multiPlayer.chatRoom;

import models.multiPlayer.Server;
import models.multiPlayer.packet.Packet;
import models.multiPlayer.packet.clientPacket.ClientChatPacket;
import models.multiPlayer.packet.serverPacket.ServerChatPacket;
import models.multiPlayer.runnables.ClientPacketListener;
import models.multiPlayer.runnables.ServerPacketListener;
import models.multiPlayer.utils.ServerConstants;

import java.util.LinkedList;

import static models.multiPlayer.packet.clientPacket.types.ClientChatPacketType.LAST_MESSAGE;
import static models.multiPlayer.packet.clientPacket.types.ClientChatPacketType.RECENT_MESSAGES;

public class ChatRoom implements ClientPacketListener<ClientChatPacket>, ServerPacketListener<ServerChatPacket> {
    private static ChatRoom instance = new ChatRoom();

    public static ChatRoom getInstance(){
        return instance;
    }

    private ChatRoom() {
    }


    private LinkedList<Message> messages = new LinkedList<>();

    @Override
    public void receive(ServerChatPacket serverChatPacket) {
        synchronized (this) {
            switch (serverChatPacket.getChatPacketType()) {
                case SEND:
                    if (messages.size() == ServerConstants.MAX_MASSAGE_HOLDING) {
                        messages.removeFirst();
                    }
                    messages.addLast(serverChatPacket.getMessage());
                    Server.getInstance().sendToAll(new ClientChatPacket(LAST_MESSAGE, serverChatPacket.getMessage()));
                    break;
                case RECEIVE_ALL:
                    Server.getInstance().sendToID(new ClientChatPacket(RECENT_MESSAGES, messages), serverChatPacket.getID());
                    break;
            }
        }
    }

    @Override
    public void receive(ClientChatPacket clientChatPacket) {
        synchronized (this) {
            switch (clientChatPacket.getChatPacketType()) {
                case RECENT_MESSAGES:
                    messages = (LinkedList<Message>) clientChatPacket.getElements()[0];
                    break;
                case LAST_MESSAGE:
                    messages.addLast((Message) clientChatPacket.getElements()[0]);
                    break;

            }
        }


    }

    public LinkedList<Message> getMessages() {
        return messages;
    }
}
