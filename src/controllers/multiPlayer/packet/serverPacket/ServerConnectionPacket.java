package controllers.multiPlayer.packet.serverPacket;

import controllers.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerConnectionPacket extends ServerPacket {
    public ServerConnectionPacket(Object... elements) {
        super(ServerPacketType.CONNECTION, elements);
    }
}
