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

    public static Resource addResources(Resource resource1, Resource resource2, Resource totalCapacity) {
        Resource totalResource = addResources(resource1, resource2);
        totalResource.setGold(Math.min(totalCapacity.getGold(), totalResource.getGold()));
        totalResource.setElixir(Math.min(totalCapacity.getElixir(), totalResource.getElixir()));
        return totalCapacity;
    }

    public static Resource subtractResources(Resource resource1, Resource resource2) {
        Resource newResource = new Resource(-resource2.getGold(), -resource2.getElixir());
        newResource.addToThisResource(resource1);
        return newResource;
    }


}

