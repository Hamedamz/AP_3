package models.multiPlayer;

import controllers.JsonHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import models.AccountInfo;
import models.GameLogic.Village;
import models.multiPlayer.packet.clientPacket.ClientInteractionPacket;
import models.multiPlayer.packet.serverPacket.ServerInteractionPacket;
import models.multiPlayer.runnables.ClientPacketListener;
import models.multiPlayer.runnables.ServerPacketListener;

import static models.multiPlayer.packet.clientPacket.types.ClientInteractionPacketType.*;
import static models.multiPlayer.packet.serverPacket.types.ServerInteractionPacketType.*;

public class InteractionManager implements ClientPacketListener<ClientInteractionPacket>,
        ServerPacketListener<ServerInteractionPacket> {
    private static InteractionManager ourInstance = new InteractionManager();

    public static InteractionManager getInstance() {
        return ourInstance;
    }

    private ObjectProperty<Village> requestedVillage = new SimpleObjectProperty<>();
    private AccountInfo requestedAccount;

    private InteractionManager() {
    }

    public void receive(ServerInteractionPacket serverInteractionPacket) {
        switch (serverInteractionPacket.getBattleManagerPacketType()) {
            case VIEW_S:
                if(serverInteractionPacket.isReceiveRequest()) {
                    Server.getInstance().sendToID(new ClientInteractionPacket(VIEW_C, true, serverInteractionPacket.getID()),
                            (String) serverInteractionPacket.getElements()[0]);
                } else {
                    Server.getInstance().sendToID(new ClientInteractionPacket(VIEW_C, false, serverInteractionPacket.getElements()[1]),
                            (String) serverInteractionPacket.getElements()[0]);
                }
                break;
            case ATTACK_S:
                if(serverInteractionPacket.isReceiveRequest()) {
                    Server.getInstance().sendToID(new ClientInteractionPacket(LOCK, true, serverInteractionPacket.getID()),
                            (String) serverInteractionPacket.getElements()[0]);
                } else {
                    Server.getInstance().sendToID(new ClientInteractionPacket(ATTACK_C, false, serverInteractionPacket.getElements()[1]),
                            (String) serverInteractionPacket.getElements()[0]);
                }
                break;
            case END_ATTACK_S:
                Server.getInstance().sendToID(new ClientInteractionPacket(END_ATTACK_C, false,
                        serverInteractionPacket.getElements()[1]), (String) serverInteractionPacket.getElements()[0]);
                break;
        }
    }

    @Override
    public void receive(ClientInteractionPacket clientInteractionPacket) {
        switch (clientInteractionPacket.getBattleManagerPacketType()) {
            case VIEW_C:
                if (clientInteractionPacket.isReceiveRequest()) {
                    Client.getInstance().sendToServer(
                            new ServerInteractionPacket(VIEW_S, false,
                                    clientInteractionPacket.getElements()[0],
                                    JsonHandler.villageToJson(Client.getInstance().getAccount().getMyVillage())
                            ));
                } else {
                    requestedVillage.set(JsonHandler.jsonToVillage((String) clientInteractionPacket.getElements()[0]));
                }
                break;
            case ATTACK_C:
                requestedVillage.set(JsonHandler.jsonToVillage((String) clientInteractionPacket.getElements()[0]));
                break;
            case LOCK:
                // TODO: 7/16/2018 lock
                requestedVillage.set(JsonHandler.jsonToVillage((String) clientInteractionPacket.getElements()[0]));
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

    public void setRequestedAccount(AccountInfo accountInfo) {
        this.requestedAccount = accountInfo;
    }

    public AccountInfo getRequestedAccount() {
        return requestedAccount;
    }
}
