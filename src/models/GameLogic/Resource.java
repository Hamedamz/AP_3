package models.GameLogic;

import java.io.Serializable;

public class Resource implements Serializable {
    private int gold;
    private int elixir;

    public Resource(int gold, int elixir) {
        this.gold = gold;
        this.elixir = elixir;
    }

    public int getGold() {
        return gold;
    }

    public int getElixir() {
        return elixir;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }

    public void addToThisResource(Resource resource) {
        gold += resource.getGold();
        elixir += resource.getElixir();
    }

    public static Resource addResources(Resource resource1, Resource resource2) {
        Resource newResource = new Resource(resource1.gold, resource1.elixir);
        newResource.addToThisResource(resource2);
        return newResource;
    }

    public static Resource addResources(Resource resource1, Resource resource2, Resource totalCapacity) {
        Resource totalResource = addResources(resource1, resource2);
        totalResource.gold = Math.min(totalCapacity.gold, totalResource.gold);
        totalResource.elixir = Math.min(totalCapacity.elixir, totalResource.elixir);
        return totalResource;
    }

    public static Resource subtractResources(Resource resource1, Resource resource2) {
        Resource newResource = new Resource(-resource2.getGold(), -resource2.getElixir());
        newResource.addToThisResource(resource1);
        return newResource;
    }

    public static int divideResources(Resource resource1, Resource resource2) {
        int goldResult, elixirResult;

        if(resource2.gold == 0) {
            goldResult = Integer.MAX_VALUE;
        } else {
            goldResult = resource1.gold/resource2.gold;
        }

        if(resource2.elixir == 0) {
            elixirResult = Integer.MAX_VALUE;
        } else {
            elixirResult = resource1.elixir/resource2.elixir;
        }

        return Math.min(goldResult, elixirResult);

    }

}

