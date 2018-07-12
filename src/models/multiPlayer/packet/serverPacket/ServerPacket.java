package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.Packet;
import models.multiPlayer.utils.FullAddress;

import java.net.InetAddress;

public class ServerPacket extends Packet {
    private ServerPacketType packetType;
    private String id;

    protected ServerPacket(ServerPacketType packetType, Object... elements) {
        super(elements);
        this.packetType = packetType;
    }

    public ServerPacketType getPacketType() {
        return packetType;
    }

    public ServerPacket withPacketType(ServerPacketType packetType) {
        this.packetType = packetType;
        return this;
    }

    public Packet withID(String id) {
        this.id = id;
        return this;
    }



    public String getID() {
        return id;
    }
}
