package models.multiPlayer.packet;

import models.multiPlayer.utils.FullAddress;

import java.io.Serializable;

public class Packet implements Serializable {
    private FullAddress fullAddress;
    private Object[] elements;

    protected Packet(Object... elements) {
        this.elements = elements;
    }

    public Packet withFullAddress(FullAddress fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    public FullAddress getFullAddress() {
        return fullAddress;
    }

    public Object[] getElements() {
        return elements;
    }
}
