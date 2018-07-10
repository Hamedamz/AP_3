package models.multiPlayer;

import models.multiPlayer.packet.serverPacket.ServerPacket;
import models.multiPlayer.runnables.PacketListener;
import models.multiPlayer.utils.ServerConstants;

import java.net.SocketException;

public class Server extends PacketHandler implements PacketListener<ServerPacket> {

    private boolean running;
    private byte[] buf = new byte[ServerConstants.SERVER_BUFFER_SIZE];

    private static Server instance;

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
        // TODO: 7/10/2018  
    }
}
