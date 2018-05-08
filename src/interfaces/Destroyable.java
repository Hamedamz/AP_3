package interfaces;

public interface Destroyable extends Locatable, Vulnerable{
    void takeDamageFromAttack(int damage);
    void destroy();
    int getMaxHitPoints();
    int getHitPoints();
}
