package models.multiPlayer.leaderBoard;

import models.AccountInfo;
import controllers.multiPlayer.Server;
import controllers.multiPlayer.packet.clientPacket.ClientLeaderBoardPacket;
import controllers.multiPlayer.packet.clientPacket.types.ClientLeaderBoardPacketType;
import controllers.multiPlayer.packet.serverPacket.ServerLeaderBoardPacket;
import controllers.multiPlayer.runnables.ClientPacketListener;
import controllers.multiPlayer.runnables.ServerPacketListener;
import models.multiPlayer.battleManger.WarLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LeaderBoard implements ClientPacketListener<ClientLeaderBoardPacket>,
        ServerPacketListener<ServerLeaderBoardPacket> {
    private static LeaderBoard instance = new LeaderBoard();

    public static LeaderBoard getInstance() {
        return instance;
    }

    private LeaderBoard() {
    }

    private ArrayList<BattleHistory> history = new ArrayList<>();
    private Map<String, AccountInfo> infoMap = new ConcurrentHashMap<>();


    public synchronized ArrayList<BattleHistory> getHistory() {
        return history;
    }

    public synchronized ArrayList<AccountInfo> getLeaderBoard(){
        ArrayList<AccountInfo> leaderBoard = new ArrayList<>();
        for (String id : infoMap.keySet()) {
            leaderBoard.add(infoMap.get(id));
        }
        Collections.sort(leaderBoard);
        return leaderBoard;
    }

    public void receive(ServerLeaderBoardPacket serverLeaderBoardPacket) {
        synchronized (this) {
            switch (serverLeaderBoardPacket.getLeaderBoardPacketType()) {

            }
        }
    }

    public void receive(ClientLeaderBoardPacket clientLeaderBoardPacket) {
        synchronized (this) {
            switch (clientLeaderBoardPacket.getLeaderBoardPacketType()) {
                case GET_LEADER_BOARD:
                    this.infoMap = (Map<String, AccountInfo>) clientLeaderBoardPacket.getElements()[0];
                    break;
                case GET_BATTLE_HiSTORIES:
                    history = (ArrayList<BattleHistory>) clientLeaderBoardPacket.getElements()[0];
                    break;
            }
        }
    }

    public synchronized void updateInfo(AccountInfo accountInfo) {
        infoMap.put(accountInfo.getId(), accountInfo);
        informAll();
    }

    public synchronized void removeInfo(String id) {
        infoMap.remove(id);
        informAll();
    }

    private void informAll(){
        Server.getInstance().sendToAll(new ClientLeaderBoardPacket(ClientLeaderBoardPacketType.GET_BATTLE_HiSTORIES,
                getHistory()));
        Server.getInstance().sendToAll(new ClientLeaderBoardPacket(ClientLeaderBoardPacketType.GET_LEADER_BOARD,
                infoMap));
    }

    public void addBattleHistory(String attackerID, String defenderID, WarLog warLog){
        BattleHistory battleHistory = new BattleHistory();
        battleHistory.setAttackerName(infoMap.get(attackerID).getName());
        battleHistory.setDefenderName(infoMap.get(defenderID).getName());
        battleHistory.setBattleTime(warLog.getTime());
        battleHistory.setLootedBounty(warLog.getBounty());
    }

    public AccountInfo getInfo(String id) {
        synchronized (this) {
            return infoMap.get(id);
        }
    }
}
