package models.multiPlayer.packet.clientPacket;

import models.multiPlayer.packet.clientPacket.types.ClientLeaderBoardPacketType;

import static models.multiPlayer.packet.clientPacket.types.ClientPacketType.LEADER_BOARD;

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
