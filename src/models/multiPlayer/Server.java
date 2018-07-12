package models.multiPlayer;

import models.multiPlayer.chatRoom.ChatRoom;
import models.multiPlayer.packet.serverPacket.ChatServerPacket;
import models.multiPlayer.packet.serverPacket.ServerPacket;
import models.multiPlayer.runnables.PacketListener;
import models.multiPlayer.utils.FullAddress;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Server extends PacketHandler implements PacketListener<ServerPacket> {
    private static Server instance;

    private Server(){
    }

    public static void initServer(int port) throws SocketException {
        instance = new Server();
        instance.initSocket(port);
//        initThreads(port);
    }

    public static Server getInstance() {
        return instance;
    }

    private Map<String, FullAddress> addressMap = new HashMap<>();

    private ChatRoom chatRoom = new ChatRoom();


    @Override
    public void receive(ServerPacket serverPacket) {
        switch (serverPacket.getPacketType()) {
            case CHAT_ROOM:
                chatRoom.receive((ChatServerPacket) serverPacket);
                break;
        }
    }
}
