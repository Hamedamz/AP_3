package models.GameLogic;

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
        Resource newResource = new Resource(resource1.getGold(), resource1.getElixir());
        newResource.addToThisResource(resource2);
        return newResource;
    }

    public static Resource subtractResources(Resource resource1, Resource resource2) {
        Resource newResource = new Resource(-resource2.getGold(), -resource2.getElixir());
        newResource.addToThisResource(resource1);
        return newResource;
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

    public int getElixir() {
        return resource.getElixir();
    }

    public Resource getResource() {
        return resource;
    }

    public void addToThisBounty(Bounty bounty) {
        score += bounty.score;
        resource.addToThisResource(bounty.resource);
    }

    public static Bounty addBounties(Bounty bounty1, Bounty bounty2) {
        Bounty newBounty = new Bounty(bounty1.score, bounty1.resource);
        newBounty.addToThisBounty(bounty2);
        return newBounty;
    }
}
