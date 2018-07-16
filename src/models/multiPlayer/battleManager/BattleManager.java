package models.multiPlayer.battleManager;

import controllers.JsonHandler;
import models.multiPlayer.Client;
import models.multiPlayer.Server;
import models.multiPlayer.packet.Packet;
import models.multiPlayer.packet.clientPacket.ClientBattleManagerPacket;
import models.multiPlayer.packet.serverPacket.ServerBattleManagerPacket;
import models.multiPlayer.runnables.PacketListener;

import static models.multiPlayer.packet.clientPacket.types.ClientBattleManagerPacketType.VIEW_C;
import static models.multiPlayer.packet.serverPacket.types.ServerBattleManagerPacketType.VIEW_S;

public class BattleManager implements PacketListener<Packet> {
    private static BattleManager ourInstance = new BattleManager();

    public static BattleManager getInstance() {
        return ourInstance;
    }

    private BattleManager() {
    }

    public void receive(ServerBattleManagerPacket serverBattleManagerPacket) {
        switch (serverBattleManagerPacket.getBattleManagerPacketType()) {
            case VIEW_S:
                if(serverBattleManagerPacket.isClientRequest()) {
                    Server.getInstance().sendToID(new ClientBattleManagerPacket(VIEW_C, true, serverBattleManagerPacket.getID()),
                            (String) serverBattleManagerPacket.getElements()[1]);
                } else {
                    Server.getInstance().sendToID(new ClientBattleManagerPacket(VIEW_C, false, serverBattleManagerPacket.getElements()[2]),
                            (String) serverBattleManagerPacket.getElements()[1]);
                }
                break;
            case ATTACK_S:
                // TODO: 7/16/2018
                break;
        }
    }

    public void receive(ClientBattleManagerPacket clientBattleManagerPacket) {
        switch (clientBattleManagerPacket.getBattleManagerPacketType()) {
            case VIEW_C:
                if(clientBattleManagerPacket.isServerRequest()) {
                    Client.getInstance().sendToServer(
                            new ServerBattleManagerPacket(VIEW_S, false,
                                    clientBattleManagerPacket.getElements()[1],
                            JsonHandler.villageToJson(Client.getInstance().getAccount().getMyVillage())
                            ));
                } else {
                    // TODO: 7/16/2018  
                }
                break;
            case ATTACK_C:
                // TODO: 7/16/2018
                break;
            case LOCK:
                // TODO: 7/16/2018
                break;
        }
    }


    @Override
    public void receive(Packet packet) {
        System.err.println("Invalid Packet at BattleManager: " + packet.getClass().getSimpleName());
    }
}
