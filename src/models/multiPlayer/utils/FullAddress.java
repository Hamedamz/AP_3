package models.multiPlayer.utils;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullAddress)) return false;
        FullAddress that = (FullAddress) o;
        return port == that.port &&
                Objects.equals(inetAddress, that.inetAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inetAddress, port);
    }
}
