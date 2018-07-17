package controllers.multiPlayer.packet.clientPacket;

import controllers.multiPlayer.packet.Packet;
import controllers.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientPacket extends Packet {
    private ClientPacketType packetType;

    protected ClientPacket(ClientPacketType packetType, Object... elements) {
        super(elements);
        this.packetType = packetType;
    }

    public ClientPacketType getPacketType() {
        return packetType;
    }
}
