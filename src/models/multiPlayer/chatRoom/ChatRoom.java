package models.multiPlayer.chatRoom;

import models.multiPlayer.packet.serverPacket.ChatServerPacket;
import models.multiPlayer.runnables.PacketListener;

import java.util.Deque;
import java.util.LinkedList;

public class ChatRoom implements PacketListener<ChatServerPacket> {

    private Deque<Message> messages = new LinkedList<>();

    @Override
    public void receive(ChatServerPacket chatServerPacket) {
        // TODO: 7/10/2018  
//        switch (chatServerPacket) {
//
//        }
//        synchronized (this) {
//            if(messages.size() == ServerConstants.MAX_MASSAGE_HOLDAGE) {
//                messages.removeFirst();
//            }
//            messages.addLast(chatServerPacket);
//        }

    }
}
