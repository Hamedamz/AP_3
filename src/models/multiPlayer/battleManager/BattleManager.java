package models.multiPlayer.battleManager;

import models.multiPlayer.packet.Packet;
import models.multiPlayer.packet.clientPacket.ClientBattleManagerPacket;
import models.multiPlayer.packet.serverPacket.ServerBattleManagerPacket;
import models.multiPlayer.runnables.PacketListener;

public class BattleManager implements PacketListener<Packet> {
    private static BattleManager ourInstance = new BattleManager();

    public static BattleManager getInstance() {
        return ourInstance;
    }

    private BattleManager() {
    }

    public void receive(ServerBattleManagerPacket serverBattleManagerPacket) {

    }

    public void receive(ClientBattleManagerPacket clientBattleManagerPacket) {

    }


    @Override
    public void receive(Packet packet) {
        System.err.println("Invalid Packet at BattleManager: " + packet.getClass().getSimpleName());
    }
}
