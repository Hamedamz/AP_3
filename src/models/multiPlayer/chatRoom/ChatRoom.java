package models.multiPlayer.chatRoom;

import models.multiPlayer.packet.serverPacket.ChatServerPacket;
import models.multiPlayer.runnables.PacketListener;
import models.multiPlayer.utils.ServerConstants;

import java.util.LinkedList;

public class ChatRoom implements PacketListener<ChatServerPacket> {

    private LinkedList<Message> messages = new LinkedList<>();

    @Override
    public void receive(ChatServerPacket chatServerPacket) {
        synchronized (this) {
            switch (chatServerPacket.getChatPacketType()) {
                case SEND:
                    if (messages.size() == ServerConstants.MAX_MASSAGE_HOLDING) {
                        messages.removeFirst();
                    }
                    messages.addLast(chatServerPacket.getMessage());
                    break;
                case RECEIVE_ALL:

                    break;
            }
        }


    }
}
