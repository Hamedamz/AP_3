package models;

import models.GameLogic.Resource;

import java.io.Serializable;

public class AccountInfo implements Serializable, Comparable<AccountInfo> {
    private String id;
    private String name;
    private int score;
    private Resource resource;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return resource.getGold();
    }

    public int getElixir() {
        return resource.getElixir();
    }

    public int getScore() {
        return score;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }


    @Override
    public int compareTo(AccountInfo o) {
        int ans = -Integer.compare(score, o.score);
        if(ans == 0) {
            ans = name.compareTo(o.name);
        }
        return ans;
    }
}
