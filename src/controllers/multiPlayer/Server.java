package controllers.multiPlayer;

import models.ConnectionManager;
import models.ConnectionType;
import models.multiPlayer.BroadcastStation;
import models.multiPlayer.InteractionManager;
import models.multiPlayer.chatRoom.ChatRoom;
import models.multiPlayer.leaderBoard.LeaderBoard;
import controllers.multiPlayer.packet.clientPacket.ClientPacket;
import controllers.multiPlayer.packet.serverPacket.*;
import controllers.multiPlayer.runnables.ServerPacketListener;
import controllers.multiPlayer.utils.FullAddress;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends PacketHandler implements ServerPacketListener<ServerPacket> {
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

    private Thread receiverThread;

    public static void initServer(int port) throws SocketException {
        instance = new Server();
        instance.initSocket(port);
        instance.initThreads();
        ConnectionManager.getInstance().setConnectionType(ConnectionType.SERVER);
    }

    private void initThreads() {
        receiverThread = new Thread(() -> {
            while (true) {
                ServerPacket serverPacket;
                try {
                    serverPacket = (ServerPacket) receiveObject();
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO: 7/14/2018
                    continue;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
                }
                if (!addressMap.containsKey(serverPacket.getID())) {
                    addressMap.put(serverPacket.getID(), serverPacket.getFullAddress());
                } else {
                    // TODO: 7/12/2018
                    addressMap.replace(serverPacket.getID(), serverPacket.getFullAddress());
                }
                receive(serverPacket);
            }
        });
        receiverThread.setDaemon(true);
        receiverThread.start();
    }


    @Override
    public void receive(ServerPacket serverPacket) {
        if(ConnectionManager.getInstance().getConnectionType().equals(ConnectionType.SERVER)) {
            LeaderBoard.getInstance().updateInfo(serverPacket.getAccountInfo());
            switch (serverPacket.getPacketType()) {
                case CHAT_ROOM:
                    ChatRoom.getInstance().receive((ServerChatPacket) serverPacket);
                    break;
                case LEADER_BOARD:
                    LeaderBoard.getInstance().receive((ServerLeaderBoardPacket) serverPacket);
                    break;
                case BATTLE_MANAGER:
                    InteractionManager.getInstance().receive((ServerInteractionPacket) serverPacket);
                    break;
                case CONNECTION:
                    ConnectionManager.getInstance().receive((ServerConnectionPacket) serverPacket);
                    break;
                case BROADCAST:
                    BroadcastStation.getInstance().receive((ServerBroadcastPacket) serverPacket);
                    break;
            }
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

    public Set<String> getIDs(){
        return new HashSet<>(addressMap.keySet());
    }


    public void disconnect(String id) {
        addressMap.remove(id);
        LeaderBoard.getInstance().removeInfo(id);
    }

    public void disconnect() {
        receiverThread.interrupt();
    }
}
