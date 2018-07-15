package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.Packet;
import models.AccountInfo;
import models.multiPlayer.packet.serverPacket.types.ServerPacketType;

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
        return accountInfo.getID();
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }
}
