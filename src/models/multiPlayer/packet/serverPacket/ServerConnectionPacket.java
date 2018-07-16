package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerConnectionPacket extends ServerPacket {
    public ServerConnectionPacket(Object... elements) {
        super(ServerPacketType.CONNECTION, elements);
    }
}
