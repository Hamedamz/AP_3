package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.Packet;

public class ServerPacket extends Packet {
    private ServerPacketType packetType;
    private String id;

    public ServerPacketType getPacketType() {
        return packetType;
    }

    public ServerPacket withPacketType(ServerPacketType packetType) {
        this.packetType = packetType;
        return this;
    }

    public ServerPacket withID(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}
