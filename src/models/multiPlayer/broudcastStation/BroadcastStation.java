package models.multiPlayer.broudcastStation;

import controllers.multiPlayer.packet.clientPacket.ClientBroadcastPacket;
import controllers.multiPlayer.packet.serverPacket.ServerBroadcastPacket;
import controllers.multiPlayer.runnables.ClientPacketListener;
import controllers.multiPlayer.runnables.ServerPacketListener;

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
