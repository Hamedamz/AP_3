package models.GameLogic.Entities.Buildings;

import models.GameLogic.Resource;

public abstract class ResourceBuilding extends Building {
    protected Resource capacity;
    protected Resource stock;

    public Resource getCapacity() {
        return capacity;
    }

    public Resource getStock() {
        return stock;
    }

    public boolean addResources(Resource resource) {

    }

    public boolean removeResources(Resource resource) {

    }

    public boolean isStorageFull() {
        Resource resource = Resource.subtractResources(capacity, stock);
        return !(resource.getElixir() == 0 && resource.getGold() == 0);
    }

    @Override
    public void takeDamageFromAttack(int damage) {
        super.takeDamageFromAttack(damage);
    }

    @Override
    public void destroy() {
        stock.setElixir(0);
        stock.setGold(0);
        super.destroy();
    }

    public void setStock(Resource stock) {
        this.stock = stock;
    }
}
