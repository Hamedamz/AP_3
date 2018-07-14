package models;

/**
 * keeps the state which we have in connections
 */
public class ConnectionManager {
    private static ConnectionManager ourInstance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
    }

    private ConnectionType connectionType;

    public static ConnectionManager getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(ConnectionManager ourInstance) {
        ConnectionManager.ourInstance = ourInstance;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }
}
