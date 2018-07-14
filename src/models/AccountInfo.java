package models;

import models.GameLogic.Resource;

import java.io.Serializable;

public class AccountInfo implements Serializable {
    private String id;
    private String name;
    private int score;

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
