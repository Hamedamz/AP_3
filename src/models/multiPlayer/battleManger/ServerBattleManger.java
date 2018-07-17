package models.multiPlayer.battleManger;

import controllers.multiPlayer.Server;
import controllers.multiPlayer.packet.clientPacket.ClientInteractionPacket;
import controllers.multiPlayer.packet.clientPacket.types.ClientInteractionPacketType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerBattleManger {
    private static ServerBattleManger ourInstance = new ServerBattleManger();

    public static ServerBattleManger getInstance() {
        return ourInstance;
    }

    private ServerBattleManger() {
    }

    //ServerOnly
    private Map<String, Battle> battleMap = new ConcurrentHashMap<>();


    private void disconnectID(String accountID) {
        for(Battle battle : battleMap.values()) {
            if(battle.containsAccountID(accountID)) {
                Server.getInstance().sendToID(new ClientInteractionPacket(ClientInteractionPacketType.END_ATTACK_C, true), battle.getAttacker().getId());
            }
        }
    }



}
