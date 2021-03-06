package controllers.multiPlayer;

import models.Account;
import models.multiPlayer.ConnectionManager;
import models.ConnectionType;
import models.multiPlayer.broudcastStation.BroadcastStation;
import models.multiPlayer.InteractionManager;
import models.multiPlayer.chatRoom.ChatRoom;
import models.multiPlayer.leaderBoard.LeaderBoard;
import controllers.multiPlayer.packet.clientPacket.*;
import controllers.multiPlayer.packet.serverPacket.ServerChatPacket;
import controllers.multiPlayer.packet.serverPacket.ServerConnectionPacket;
import controllers.multiPlayer.packet.serverPacket.types.ServerChatPacketType;
import controllers.multiPlayer.packet.serverPacket.ServerPacket;
import controllers.multiPlayer.runnables.ClientPacketListener;
import controllers.multiPlayer.utils.FullAddress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

public class Client extends PacketHandler implements ClientPacketListener<ClientPacket> {

    private static Client instance;

    private Client() {
    }

    public static Client getInstance() {
        return instance;
    }

    private Account account;
    private FullAddress serverAddress;

    public static void initClient(int port) throws SocketException {
        instance = new Client();
        instance.initSocket(port);
        instance.initThreads();
    }

    public void setupConnection(InetAddress serverInetAddress, int serverPort) {
        serverAddress = new FullAddress(serverInetAddress, serverPort);
        ConnectionManager.getInstance().setConnectionType(ConnectionType.CLIENT);
        sendToServer(new ServerConnectionPacket());
        sendToServer(new ServerChatPacket(ServerChatPacketType.RECEIVE_ALL));
    }

    private Thread receiverThread;

    public void initThreads() {
        receiverThread = new Thread(() -> {
            while (true) {
                ClientPacket clientPacket;
                try {
                    clientPacket = (ClientPacket) receiveObject();
                } catch (IOException e) {
                    // TODO: 7/14/2018
                    continue;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
                }
                if (clientPacket.getFullAddress().equals(serverAddress)) {
                    receive(clientPacket);
                }
            }
        });
        receiverThread.setDaemon(true);
        receiverThread.start();
    }

    @Override
    public void receive(ClientPacket clientPacket) {
        if(ConnectionManager.getInstance().getConnectionType().equals(ConnectionType.CLIENT)) {
            switch (clientPacket.getPacketType()) {
                case CHAT_ROOM:
                    ChatRoom.getInstance().receive((ClientChatPacket) clientPacket);
                    break;
                case LEADER_BOARD:
                    LeaderBoard.getInstance().receive((ClientLeaderBoardPacket) clientPacket);
                    break;
                case INTERACTION:
                    InteractionManager.getInstance().receive((ClientInteractionPacket) clientPacket);
                    break;
                // TODO: 7/14/2018
                case CONNECTION:
                    ConnectionManager.getInstance().receive((ClientConnectionPacket) clientPacket);
                    break;
                case BROADCAST:
                    BroadcastStation.getInstance().receive((ClientBroadcastPacket) clientPacket);
                    break;
            }
        }
    }

    public void sendToServer(ServerPacket serverPacket) {
        try {
            sendObject(serverPacket.withAccountInfo(account.getInfo()), serverAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Account getAccount() {
        return account;
    }

    public FullAddress getServerAddress() {
        return serverAddress;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void disconnect() {
        receiverThread.interrupt();
        getSocket().close();
    }
}
