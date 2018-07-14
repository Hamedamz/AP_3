package models.multiPlayer.leaderBoard;

import models.multiPlayer.packet.serverPacket.ServerLeaderBoardPacket;
import models.multiPlayer.runnables.PacketListener;

import java.util.ArrayList;

public class LeaderBoard implements PacketListener<ServerLeaderBoardPacket> {
    private ArrayList<BattleHistory> history = new ArrayList<>();

    @Override
    public void receive(ServerLeaderBoardPacket serverLeaderBoardPacket) {
        switch (serverLeaderBoardPacket.getLeaderBoardPacketType()) {

        }
    }
}
