package models.multiPlayer.battleManger;

import controllers.multiPlayer.Server;
import controllers.multiPlayer.packet.clientPacket.ClientInteractionPacket;
import controllers.multiPlayer.packet.clientPacket.types.ClientInteractionPacketType;
import models.multiPlayer.leaderBoard.LeaderBoard;

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


    public void disconnectID(String accountID) {
        for(Battle battle : battleMap.values()) {
            if(battle.containsAccountID(accountID)) {
                Server.getInstance().sendToID(new ClientInteractionPacket(ClientInteractionPacketType.END_ATTACK_C, true), battle.getAttacker().getId());
            }
        }
    }


    public void makeNewBattle(String attackerID, String defenderID) {
        Battle battle = new Battle(LeaderBoard.getInstance().getInfo(attackerID), LeaderBoard.getInstance().getInfo(defenderID));
        battleMap.put(battle.getBattleID(), battle);
    }
}
