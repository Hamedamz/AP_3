package models;

import controllers.JsonHandler;
import models.GameLogic.Village;

import java.io.Serializable;
import java.util.UUID;

public class Account implements Serializable {
    private String userName;
    private String password;
    private String id;

    private Village myVillage;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.id = UUID.randomUUID().toString();
        myVillage = Village.startNewVillage();
    }

    public Village getMyVillage() {
        return myVillage;
    }

    public void setMyVillage(Village myVillage) {
        this.myVillage = myVillage;
    }

    public boolean checkPassword (String password) {
        return this.password.equals(password);
    }

    public String getUserName() {
        return userName;
    }

    public String getId() {
        return id;
    }

    public AccountInfo getInfo() {
        AccountInfo info = new AccountInfo();
        info.setName(userName);
        info.setID(id);
        info.setScore(myVillage.getTownHall().getVillageScore());
        return info;
    }

    public void updateAcoount(String jsonVillage) {
        this.myVillage = JsonHandler.jsonToVillage(jsonVillage);
    }

}
