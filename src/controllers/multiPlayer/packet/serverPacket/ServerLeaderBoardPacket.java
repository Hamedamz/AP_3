package controllers.multiPlayer.packet.serverPacket;

import controllers.multiPlayer.packet.serverPacket.types.ServerLeaderBoardPacketType;
import controllers.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerLeaderBoardPacket extends ServerPacket {
    private ServerLeaderBoardPacketType leaderBoardPacketType;

    protected ServerLeaderBoardPacket(ServerLeaderBoardPacketType lbPacketType, Object... elements) {
        super(ServerPacketType.LEADER_BOARD, elements);
        leaderBoardPacketType = lbPacketType;
    }

    public ServerLeaderBoardPacketType getLeaderBoardPacketType() {
        return leaderBoardPacketType;
    }
}
