package interfaces;

public interface Destroyable {
    void takeDamageFromAttack(int damage);
    void destroy();
    boolean isDestroyed();
}
