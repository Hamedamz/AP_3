package models.multiPlayer.packet.serverPacket;

import models.multiPlayer.packet.serverPacket.types.ServerBattleManagerPacketType;
import models.multiPlayer.packet.serverPacket.types.ServerPacketType;

public class ServerBattleManagerPacket extends ServerPacket {
    private ServerBattleManagerPacketType battleManagerPacketType;

    public ServerBattleManagerPacket(ServerBattleManagerPacketType battleManagerPacketType, Object... elements) {
        super(ServerPacketType.BATTLE_MANAGER, elements);
        this.battleManagerPacketType = battleManagerPacketType;
    }

    public ServerBattleManagerPacketType getBattleManagerPacketType() {
        return battleManagerPacketType;
    }
}
