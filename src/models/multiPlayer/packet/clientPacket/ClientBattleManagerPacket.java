package models.multiPlayer.packet.clientPacket;

import models.multiPlayer.packet.clientPacket.types.ClientBattleManagerPacketType;
import models.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientBattleManagerPacket extends ClientPacket {
    private ClientBattleManagerPacketType battleManagerPacketType;

    public ClientBattleManagerPacket(ClientBattleManagerPacketType battleManagerPacketType, Object... elements) {
        super(ClientPacketType.BATTLE_MANAGER, elements);
        this.battleManagerPacketType = battleManagerPacketType;
    }

    public ClientBattleManagerPacketType getBattleManagerPacketType() {
        return battleManagerPacketType;
    }
}
