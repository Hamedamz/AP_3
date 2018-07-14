package models.multiPlayer.chatRoom;

import models.multiPlayer.Server;
import models.multiPlayer.packet.clientPacket.ClientChatPacket;
import models.multiPlayer.packet.serverPacket.ServerChatPacket;
import models.multiPlayer.runnables.PacketListener;
import models.multiPlayer.utils.ServerConstants;

import java.util.LinkedList;

import static models.multiPlayer.packet.clientPacket.ClientChatPacketType.LAST_MESSAGE;
import static models.multiPlayer.packet.clientPacket.ClientChatPacketType.RECENT_MESSAGES;

public class ChatRoom implements PacketListener<ServerChatPacket> {

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
}
