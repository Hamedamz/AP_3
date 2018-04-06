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
}

class Bounty {
    private int score;
    private Resource resource;

    public Bounty(int score, Resource cost) {
        this.score = score;
        this.resource = cost;
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
}
