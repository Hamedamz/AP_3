package controllers.multiPlayer.packet.serverPacket;

import controllers.multiPlayer.packet.Packet;
import models.AccountInfo;
import controllers.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerPacket extends Packet {
    private ServerPacketType packetType;
    private AccountInfo accountInfo;

    protected ServerPacket(ServerPacketType packetType, Object... elements) {
        super(elements);
        this.packetType = packetType;
        accountInfo = new AccountInfo();
    }

    public ServerPacketType getPacketType() {
        return packetType;
    }

    public ServerPacket withAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        return this;
    }

    public String getID() {
        return accountInfo.getId();
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }
}
