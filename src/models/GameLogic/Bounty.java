package models.GameLogic;

public class Bounty {
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
