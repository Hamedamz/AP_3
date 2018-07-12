package models.multiPlayer.chatRoom;

import models.multiPlayer.Server;
import models.multiPlayer.packet.clientPacket.ClientPacket;
import models.multiPlayer.packet.clientPacket.ClientPacketType;
import models.multiPlayer.packet.serverPacket.ServerChatPacket;
import models.multiPlayer.runnables.PacketListener;
import models.multiPlayer.utils.ServerConstants;

import java.util.LinkedList;

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
                    // TODO: 7/12/2018  
                    //Server.getInstance().sendToAll();
                    break;
                case RECEIVE_ALL:

                    break;
            }
        }


    }
}
