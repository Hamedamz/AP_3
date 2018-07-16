package models.multiPlayer.packet.clientPacket;

import models.multiPlayer.packet.clientPacket.types.ClientBattleManagerPacketType;
import models.multiPlayer.packet.clientPacket.types.ClientPacketType;

public class ClientBattleManagerPacket extends ClientPacket {
    private ClientBattleManagerPacketType battleManagerPacketType;
    private boolean isRequest;

    public ClientBattleManagerPacket(ClientBattleManagerPacketType battleManagerPacketType, boolean isRequest, Object... elements) {
        super(ClientPacketType.BATTLE_MANAGER, elements);
        this.battleManagerPacketType = battleManagerPacketType;
        this.isRequest = isRequest;
    }

    public ClientBattleManagerPacketType getBattleManagerPacketType() {
        return battleManagerPacketType;
    }

    public boolean isReceiveRequest(){
        return isRequest;
    }
}
