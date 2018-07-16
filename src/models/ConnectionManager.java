package models;

import models.multiPlayer.packet.Packet;
import models.multiPlayer.packet.clientPacket.ClientConnectionPacket;
import models.multiPlayer.packet.clientPacket.ClientPacket;
import models.multiPlayer.packet.serverPacket.ServerConnectionPacket;
import models.multiPlayer.runnables.ClientPacketListener;
import models.multiPlayer.runnables.ServerPacketListener;

/**
 * keeps the state which we have in connections
 */
public class ConnectionManager implements ClientPacketListener<ClientConnectionPacket>,
        ServerPacketListener<ServerConnectionPacket> {
    private static ConnectionManager instance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return instance;
    }

    private ConnectionManager() {
    }

    private ConnectionType connectionType;

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    @Override
    public void receive(ClientConnectionPacket clientConnectionPacket) {

    }

    @Override
    public void receive(ServerConnectionPacket serverConnectionPacket) {

    }
    
    private void disconnect() {
        // TODO: 7/16/2018  
    }

}
