package models.multiPlayer.leaderBoard;

import models.AccountInfo;
import models.multiPlayer.Server;
import models.multiPlayer.packet.Packet;
import models.multiPlayer.packet.clientPacket.ClientLeaderBoardPacket;
import models.multiPlayer.packet.clientPacket.types.ClientLeaderBoardPacketType;
import models.multiPlayer.packet.serverPacket.ServerLeaderBoardPacket;
import models.multiPlayer.runnables.ClientPacketListener;
import models.multiPlayer.runnables.ServerPacketListener;

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
}
