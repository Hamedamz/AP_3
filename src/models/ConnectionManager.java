package models;

import controllers.multiPlayer.Client;
import controllers.multiPlayer.Server;
import controllers.multiPlayer.packet.clientPacket.ClientConnectionPacket;
import controllers.multiPlayer.packet.serverPacket.ServerConnectionPacket;
import controllers.multiPlayer.runnables.ClientPacketListener;
import controllers.multiPlayer.runnables.ServerPacketListener;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * keeps the state which we have in connections
 */
public class ConnectionManager implements ClientPacketListener<ClientConnectionPacket>,
        ServerPacketListener<ServerConnectionPacket> {
    public static final int SERVER_CONNECTION_TIMEOUT_DURATION = 2000;

    private static ConnectionManager instance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return instance;
    }

    private ConnectionType connectionType;
    private Timer connectionTimer;

    //for client only
    private int connectionTimeout = 0;

    //for server only
    private Set<String> disConnectedIds;

    private ConnectionManager() {
        connectionType = ConnectionType.NONE;
        connectionTimer = new Timer(true);
        disConnectedIds = new HashSet<>();
        connectionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (this) {
                    switch (connectionType) {
                        case CLIENT:
                            if (connectionTimeout > 1) {
                                disconnect();
                            } else {
                                connectionTimeout++;
                            }
                            break;
                        case SERVER:
                            for(String id : disConnectedIds) {
                                Server.getInstance().disconnect(id);
                            }
                            disConnectedIds = Server.getInstance().getIDs();
                            Server.getInstance().sendToAll(new ClientConnectionPacket());
                            // TODO: 7/16/2018
                            break;
                    }
                }
            }
        }, 0, SERVER_CONNECTION_TIMEOUT_DURATION);
    }


    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public synchronized void receive(ClientConnectionPacket clientConnectionPacket) {
        Client.getInstance().sendToServer(new ServerConnectionPacket());
        connectionTimeout = 0;
    }

    @Override
    public synchronized void receive(ServerConnectionPacket serverConnectionPacket) {
        disConnectedIds.remove(serverConnectionPacket.getID());
    }

    public synchronized void disconnect() {
        connectionTimeout = 0;
        if(connectionType.equals(ConnectionType.CLIENT)) {
            Client.getInstance().disconnect();
        }
        if(connectionType.equals(ConnectionType.SERVER)) {
            Server.getInstance().disconnect();
        }
        setConnectionType(ConnectionType.NONE);
        // TODO: 7/16/2018
    }

}
