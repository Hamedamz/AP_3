package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerBroadcastPacket extends ServerPacket {
    protected ServerBroadcastPacket(ServerPacketType packetType, Object... elements) {
        super(packetType, elements);
    }
}
