package models.multiPlayer.battleManager;

import controllers.JsonHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import models.GameLogic.BattleGround;
import models.GameLogic.Village;
import models.multiPlayer.Client;
import models.multiPlayer.Server;
import models.multiPlayer.packet.clientPacket.ClientBattleManagerPacket;
import models.multiPlayer.packet.serverPacket.ServerBattleManagerPacket;
import models.multiPlayer.runnables.ClientPacketListener;
import models.multiPlayer.runnables.ServerPacketListener;

import static models.multiPlayer.packet.clientPacket.types.ClientBattleManagerPacketType.*;
import static models.multiPlayer.packet.serverPacket.types.ServerBattleManagerPacketType.*;

public class BattleManager implements ClientPacketListener<ClientBattleManagerPacket>,
        ServerPacketListener<ServerBattleManagerPacket> {
    private static BattleManager ourInstance = new BattleManager();

    public static BattleManager getInstance() {
        return ourInstance;
    }

    private ObjectProperty<Village> requestedVillage = new SimpleObjectProperty<>();

    private BattleManager() {
    }

    public void receive(ServerBattleManagerPacket serverBattleManagerPacket) {
        switch (serverBattleManagerPacket.getBattleManagerPacketType()) {
            case VIEW_S:
                if(serverBattleManagerPacket.isReceiveRequest()) {
                    Server.getInstance().sendToID(new ClientBattleManagerPacket(VIEW_C, true, serverBattleManagerPacket.getID()),
                            (String) serverBattleManagerPacket.getElements()[0]);
                } else {
                    Server.getInstance().sendToID(new ClientBattleManagerPacket(VIEW_C, false, serverBattleManagerPacket.getElements()[1]),
                            (String) serverBattleManagerPacket.getElements()[0]);
                }
                break;
            case ATTACK_S:
                if(serverBattleManagerPacket.isReceiveRequest()) {
                    Server.getInstance().sendToID(new ClientBattleManagerPacket(LOCK, true, serverBattleManagerPacket.getID()),
                            (String) serverBattleManagerPacket.getElements()[0]);
                } else {
                    Server.getInstance().sendToID(new ClientBattleManagerPacket(ATTACK_C, false, serverBattleManagerPacket.getElements()[1]),
                            (String) serverBattleManagerPacket.getElements()[0]);
                }
                break;
            case END_ATTACK_S:
                Server.getInstance().sendToID(new ClientBattleManagerPacket(END_ATTACK_C, false,
                        serverBattleManagerPacket.getElements()[1]), (String) serverBattleManagerPacket.getElements()[0]);
                break;
        }
    }

    @Override
    public void receive(ClientBattleManagerPacket clientBattleManagerPacket) {
        switch (clientBattleManagerPacket.getBattleManagerPacketType()) {
            case VIEW_C:
                if (clientBattleManagerPacket.isReceiveRequest()) {
                    Client.getInstance().sendToServer(
                            new ServerBattleManagerPacket(VIEW_S, false,
                                    clientBattleManagerPacket.getElements()[0],
                                    JsonHandler.villageToJson(Client.getInstance().getAccount().getMyVillage())
                            ));
                } else {
                    requestedVillage.set(JsonHandler.jsonToVillage((String) clientBattleManagerPacket.getElements()[0]));
                }
                break;
            case ATTACK_C:
                requestedVillage.set(JsonHandler.jsonToVillage((String) clientBattleManagerPacket.getElements()[0]));
                break;
            case LOCK:
                // TODO: 7/16/2018 lock
                requestedVillage.set(JsonHandler.jsonToVillage((String) clientBattleManagerPacket.getElements()[0]));
                break;
            case END_ATTACK_C:
                // TODO: 7/16/2018 merging villages
                break;
        }
    }

    public Village getRequestedVillage() {
        return requestedVillage.get();
    }

    public ObjectProperty<Village> requestedVillageProperty() {
        return requestedVillage;
    }
}
