package models.multiPlayer.packet.clientPacket;

import static models.multiPlayer.packet.clientPacket.ClientPacketType.LEADER_BOARD;

public class ClientLeaderBoardPacket extends ClientPacket {
    private ClientLeaderBoardPacketType leaderBoardPacketType;

    public ClientLeaderBoardPacket(ClientLeaderBoardPacketType leaderBoardPacketType, Object... elements) {
        super(LEADER_BOARD, elements);
        this.leaderBoardPacketType = leaderBoardPacketType;
    }

    public ClientLeaderBoardPacketType getLeaderBoardPacketType() {
        return leaderBoardPacketType;
    }
}
