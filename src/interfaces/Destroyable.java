package interfaces;

public interface Destroyable extends Locatable{
    void takeDamageFromAttack(int damage);
    void destroy();
    boolean isDestroyed();
    int getMaxHitPoints();
    int getHitPoints();
}
