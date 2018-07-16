package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.serverPacket.types.ServerBattleManagerPacketType;
import models.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerBattleManagerPacket extends ServerPacket {
    private ServerBattleManagerPacketType battleManagerPacketType;
    private boolean isRequest;

    public ServerBattleManagerPacket(ServerBattleManagerPacketType battleManagerPacketType, boolean isRequest, Object... elements) {
        super(ServerPacketType.BATTLE_MANAGER, elements);
        this.battleManagerPacketType = battleManagerPacketType;
        this.isRequest = isRequest;
    }

    public ServerBattleManagerPacketType getBattleManagerPacketType() {
        return battleManagerPacketType;
    }

    public boolean isReceiveRequest(){
        return isRequest;
    }
}
