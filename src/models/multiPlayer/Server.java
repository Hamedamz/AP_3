package models.multiPlayer;

import models.multiPlayer.chatRoom.ChatRoom;
import models.multiPlayer.packet.serverPacket.ChatServerPacket;
import models.multiPlayer.packet.serverPacket.ServerPacket;
import models.multiPlayer.runnables.PacketListener;
import models.multiPlayer.utils.ServerConstants;

import java.net.SocketException;
import java.util.Map;

public class Server extends PacketHandler implements PacketListener<ServerPacket> {
    private static Server instance;


    private ChatRoom chatRoom = new ChatRoom();

    private Server() throws SocketException {
    }

    public static void initiateServer(int port) throws SocketException {
        instance = new Server();
        instance.initiateSocket(port);
    }

    public static Server getInstance() {
        return instance;
    }


    @Override
    public void receive(ServerPacket serverPacket) {
        switch (serverPacket.getPacketType()) {
            case CHAT_ROOM:
                chatRoom.receive((ChatServerPacket) serverPacket);
                break;
        }
    }
}
