package models.multiPlayer.packet.serverPacket;

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
