package controllers.multiPlayer.packet.clientPacket;

import controllers.multiPlayer.packet.clientPacket.types.ClientLeaderBoardPacketType;

import static controllers.multiPlayer.packet.clientPacket.types.ClientPacketType.LEADER_BOARD;

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
