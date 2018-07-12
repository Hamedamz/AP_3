package models.multiPlayer;

import models.multiPlayer.chatRoom.ChatRoom;
import models.multiPlayer.packet.clientPacket.ClientPacket;
import models.multiPlayer.packet.serverPacket.ServerChatPacket;
import models.multiPlayer.packet.serverPacket.ServerPacket;
import models.multiPlayer.runnables.PacketListener;
import models.multiPlayer.utils.FullAddress;

import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends PacketHandler implements PacketListener<ServerPacket> {
    private static Server instance;

    private Server(){
    }

    public static Server getInstance() {
        return instance;
    }

    /**
     * Maps IDs to FullAddress,
     * each time a packet is received, packet id and fullAddress is added to this map
     */
    private Map<String, FullAddress> addressMap = new ConcurrentHashMap<>();

    private ChatRoom chatRoom = new ChatRoom();

    private Thread receiverThread;

    public static void initServer(int port) throws SocketException {
        instance = new Server();
        instance.initSocket(port);
        instance.initThreads();
    }

    private void initThreads() {
        receiverThread = new Thread(() -> {
            while (true) {
                ServerPacket serverPacket = (ServerPacket) recieveObject();
                receive(serverPacket);
                if (!addressMap.containsKey(serverPacket.getID())) {
                    addressMap.put(serverPacket.getID(), serverPacket.getFullAddress());
                } else {
                    // TODO: 7/12/2018  
                    addressMap.replace(serverPacket.getID(), serverPacket.getFullAddress());
                }
            }
        });
        receiverThread.start();
    }


    @Override
    public void receive(ServerPacket serverPacket) {
        switch (serverPacket.getPacketType()) {
            case CHAT_ROOM:
                chatRoom.receive((ServerChatPacket) serverPacket);
                break;
        }
    }
    
    public void sendToAll(ClientPacket clientPacket) {
        for(String id : addressMap.keySet()) {
            FullAddress fullAddress = addressMap.get(id);
            sendObject(clientPacket, fullAddress.getInetAddress(), fullAddress.getPort());
        }
    }
}
