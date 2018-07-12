package models.multiPlayer.utils;

import java.io.Serializable;
import java.net.InetAddress;

public class FullAddress implements Serializable {
    private InetAddress inetAddress;
    private int port;

    public FullAddress(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public int getPort() {
        return port;
    }
}
