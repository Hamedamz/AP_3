package models.multiPlayer;

import models.multiPlayer.chatRoom.ChatRoom;
import models.multiPlayer.leaderBoard.LeaderBoard;
import models.multiPlayer.packet.clientPacket.ClientPacket;
import models.multiPlayer.packet.serverPacket.ServerChatPacket;
import models.multiPlayer.packet.serverPacket.ServerPacket;
import models.multiPlayer.runnables.PacketListener;
import models.AccountInfo;
import models.multiPlayer.utils.FullAddress;

import java.io.IOException;
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
    private Map<String, AccountInfo> infoMap = new ConcurrentHashMap<>();

    private ChatRoom chatRoom = new ChatRoom();
    private LeaderBoard leaderBoard = new LeaderBoard();

    private Thread receiverThread;

    public static void initServer(int port) throws SocketException {
        instance = new Server();
        instance.initSocket(port);
        instance.initThreads();
    }

    private void initThreads() {
        receiverThread = new Thread(() -> {
            while (true) {
                ServerPacket serverPacket = null;
                try {
                    serverPacket = (ServerPacket) receiveObject();
                } catch (IOException e) {
                    // TODO: 7/14/2018
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (!addressMap.containsKey(serverPacket.getID())) {
                    addressMap.put(serverPacket.getID(), serverPacket.getFullAddress());
                    infoMap.put(serverPacket.getID(), serverPacket.getAccountInfo());
                } else {
                    // TODO: 7/12/2018
                    addressMap.replace(serverPacket.getID(), serverPacket.getFullAddress());
                }
                receive(serverPacket);
            }
        });
        receiverThread.start();
    }


    @Override
    public void receive(ServerPacket serverPacket) {
        updateInfo(serverPacket.getAccountInfo());
        switch (serverPacket.getPacketType()) {
            case CHAT_ROOM:
                chatRoom.receive((ServerChatPacket) serverPacket);
                break;
            case LEADER_BOARD:

        }
    }

    public void sendToID(ClientPacket clientPacket, String id)  {
        try {
            sendObject(clientPacket, addressMap.get(id));
        } catch (IOException e) {
            disconnect(id);
        }
    }

    public void sendToAll(ClientPacket clientPacket) {
        for(String id : addressMap.keySet()) {
            sendToID(clientPacket, id);
        }
    }

    private void updateInfo(AccountInfo accountInfo) {
        infoMap.replace(accountInfo.getID(), accountInfo);
    }

    public void disconnect(String id) {
        addressMap.remove(id);
        infoMap.remove(id);
    }
}
