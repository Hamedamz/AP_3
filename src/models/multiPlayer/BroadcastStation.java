package models.multiPlayer;

import models.multiPlayer.packet.clientPacket.ClientBroadcastPacket;
import models.multiPlayer.packet.serverPacket.ServerBroadcastPacket;
import models.multiPlayer.runnables.ClientPacketListener;
import models.multiPlayer.runnables.ServerPacketListener;

public class BroadcastStation implements ServerPacketListener<ServerBroadcastPacket>,
        ClientPacketListener<ClientBroadcastPacket> {
    private static BroadcastStation instance = new BroadcastStation();

    public static BroadcastStation getInstance(){
        return instance;
    }

    private BroadcastStation(){
    }


    @Override
    public void receive(ClientBroadcastPacket clientBroadcastPacket) {

    }

    @Override
    public void receive(ServerBroadcastPacket serverBroadcastPacket) {

    }
}
