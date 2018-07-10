package models.multiPlayer.runnables;

import models.multiPlayer.packet.Packet;

import java.io.ObjectInputStream;

/**
 * Created by mahdihs76 on 5/21/18.
 */
public class GetDataRunnable<T extends Packet> implements Runnable {

    private ObjectInputStream inputStream;
    private PacketListener<T> listener;

    public GetDataRunnable(ObjectInputStream inputStream, PacketListener<T> listener) {
        this.inputStream = inputStream;
        this.listener = listener;
    }

    @Override
    public void run() {
        while (true) {
            try {
                T t = (T) inputStream.readUnshared();
                listener.receive(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
