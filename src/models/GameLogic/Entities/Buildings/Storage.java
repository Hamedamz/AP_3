package models.GameLogic.Entities.Buildings;

import models.GameLogic.Position;
import models.GameLogic.Resource;
import models.ID;
import models.Setting.GameLogicConfig;

import java.util.Comparator;

public abstract class Storage extends ResourceBuilding {
    protected Resource capacity;
    protected Resource stock;

    public Storage(Position position, ID id) {
        super(position, id);
        String className = this.getClass().getSimpleName();
        this.capacity = new Resource((int) GameLogicConfig.getFromDictionary(className + "GoldCapacity"), (int) GameLogicConfig.getFromDictionary(className + "ElixirCapacity"));
        //this.stock = ;  FIXME complete me
    }

    public Resource getCapacity() {
        return capacity;
    }

    public void setStock(Resource stock) {
        this.stock = stock;
    }

    public void setCapacity(Resource capacity) {
        this.capacity = capacity;
    }


    public Resource getStock() {
        return stock;
    }

    public void setGold(int gold) {
        stock.setGold(Math.min(gold, capacity.getGold()));
    }

    public void setElixir(int elixir) {
        stock.setGold(Math.min(elixir, capacity.getElixir()));
    }

    public boolean addResources(Resource resource) {
        return true;
        // TODO: 5/5/2018  
    }

    public boolean removeResources(Resource resource) {
        return true;
        // TODO: 5/5/2018  
    }


    public Resource drainStock() {
        Resource res = stock;
        stock = new Resource(0, 0);
        return res;
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

    public static class GoldStorageComparator implements Comparator<Storage> {

        @Override
        public int compare(Storage o1, Storage o2) {
            return o1.getCapacity().getGold() - o2.getCapacity().getGold();
        }
    }

    public static class ElixirStorageComparator implements Comparator<Storage> {

        @Override
        public int compare(Storage o1, Storage o2) {
            return o1.getCapacity().getElixir() - o2.getCapacity().getElixir();

        }
    }

}

