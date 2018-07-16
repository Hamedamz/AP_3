package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.serverPacket.types.ServerBattleManagerPacketType;
import models.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerBattleManagerPacket extends ServerPacket {
    private ServerBattleManagerPacketType battleManagerPacketType;

    public ServerBattleManagerPacket(ServerBattleManagerPacketType battleManagerPacketType, boolean isRequest, Object... elements) {
        super(ServerPacketType.BATTLE_MANAGER, isRequest, elements);
        this.battleManagerPacketType = battleManagerPacketType;
    }

    public ServerBattleManagerPacketType getBattleManagerPacketType() {
        return battleManagerPacketType;
    }

    public boolean isReceiveRequest(){
        return (boolean) getElements()[0];
    }
}
