package models.multiPlayer;

import models.multiPlayer.packet.Packet;
import models.multiPlayer.utils.FullAddress;
import models.multiPlayer.utils.ServerConstants;

import java.io.*;
import java.net.*;

public class PacketHandler {
    private DatagramSocket socket;

    protected void initSocket(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public void sendObject(Packet o, InetAddress address, int desPort) {
        try {
            ByteArrayOutputStream byteStream = new
                    ByteArrayOutputStream(ServerConstants.SERVER_BUFFER_SIZE);
            ObjectOutputStream os = new ObjectOutputStream(new
                    BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(o);
            os.flush();
            //retrieves byte array
            byte[] sendBuf = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(
                    sendBuf, sendBuf.length, address, desPort);
            int byteCount = packet.getLength();
            socket.send(packet);
            os.close();
        } catch (UnknownHostException e) {
            System.err.println("Exception:  " + e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Packet recieveObject() {
        try {
            byte[] recvBuf = new byte[ServerConstants.SERVER_BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(recvBuf,
                    recvBuf.length);
            socket.receive(packet);
            int byteCount = packet.getLength();
            ByteArrayInputStream byteStream = new
                    ByteArrayInputStream(recvBuf);
            ObjectInputStream is = new
                    ObjectInputStream(new BufferedInputStream(byteStream));
            Object o = is.readObject();
            is.close();
            return ((Packet) o).withFullAddress(new FullAddress(packet.getAddress(), packet.getPort()));
        } catch (IOException e) {
            System.err.println("Exception:  " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (null);
    }
}
