package models.multiPlayer.leaderBoard;

import models.AccountInfo;
import models.multiPlayer.Server;
import models.multiPlayer.packet.Packet;
import models.multiPlayer.packet.clientPacket.ClientLeaderBoardPacket;
import models.multiPlayer.packet.clientPacket.ClientLeaderBoardPacketType;
import models.multiPlayer.packet.serverPacket.ServerLeaderBoardPacket;
import models.multiPlayer.runnables.PacketListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LeaderBoard implements PacketListener<Packet> {
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
        return leaderBoard;
    }

    public void receive(ServerLeaderBoardPacket serverLeaderBoardPacket) {
        synchronized (this) {
            switch (serverLeaderBoardPacket.getLeaderBoardPacketType()) {
                case GET_BATTLE_HiSTORIES:
                    Server.getInstance().sendToID(new ClientLeaderBoardPacket(ClientLeaderBoardPacketType.GET_BATTLE_HiSTORIES,
                            getHistory()), serverLeaderBoardPacket.getID());
                    break;
                case GET_LEADER_BOARD:
                    Server.getInstance().sendToID(new ClientLeaderBoardPacket(ClientLeaderBoardPacketType.GET_LEADER_BOARD,
                            getLeaderBoard()), serverLeaderBoardPacket.getID());
                    break;
            }
        }
    }

    public void receive(ClientLeaderBoardPacket clientLeaderBoardPacket) {
        synchronized (this) {
            switch (clientLeaderBoardPacket.getLeaderBoardPacketType()) {
                case GET_LEADER_BOARD:

                    break;
                case GET_BATTLE_HiSTORIES:

                    break;
            }
        }
    }

    @Override
    public void receive(Packet packet) {
        System.err.println("Invalid Packet in LeaderBoard: " + packet.getClass().getSimpleName());
    }

    public synchronized void updateInfo(AccountInfo accountInfo) {
        infoMap.put(accountInfo.getID(), accountInfo);
    }

    public synchronized void removeInfo(String id) {
        infoMap.remove(id);
    }
}
