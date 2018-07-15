package models.multiPlayer.leaderBoard;

import models.multiPlayer.packet.serverPacket.ServerLeaderBoardPacket;
import models.multiPlayer.runnables.PacketListener;

import java.util.ArrayList;

public class LeaderBoard implements PacketListener<ServerLeaderBoardPacket> {
    private static LeaderBoard instance = new LeaderBoard();

    public static LeaderBoard getInstance() {
        return instance;
    }

    private LeaderBoard() {
    }

    private ArrayList<BattleHistory> history = new ArrayList<>();

    @Override
    public void receive(ServerLeaderBoardPacket serverLeaderBoardPacket) {
        switch (serverLeaderBoardPacket.getLeaderBoardPacketType()) {
            case GET_BATTLE_HiSTORIES:
                // TODO: 7/14/2018
                break;
            case GET_LEADER_BOARD:
                // TODO: 7/14/2018  
                break;
        }
    }
}
