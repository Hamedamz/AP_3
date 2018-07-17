package controllers.multiPlayer.packet.serverPacket;

import controllers.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerBroadcastPacket extends ServerPacket {
    protected ServerBroadcastPacket(ServerPacketType packetType, Object... elements) {
        super(packetType, elements);
    }
}
