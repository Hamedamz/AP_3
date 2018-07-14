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

        }
    }
}
