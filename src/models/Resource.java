package models;

public class Resource {
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

    public void addToThisResource(Resource resource) {

    }

    public static Resource addResources(Resource resource1, Resource resource2) {

    }
}

class Bounty {
    private int score;
    private Resource resource;

    public Bounty(int score, Resource resource) {
        this.score = score;
        this.resource = resource;
    }

    public int getScore() {
        return score;
    }

    public int getGold() {
        return resource.getGold();
    }

    private int getElixir() {
        return resource.getElixir();
    }

    public void addToThisBounty(Bounty bounty) {

    }

    public static Bounty addResources(Bounty bounty1, Bounty bounty2) {

    }
}
