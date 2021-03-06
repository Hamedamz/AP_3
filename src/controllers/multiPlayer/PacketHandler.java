package controllers.multiPlayer;

import controllers.multiPlayer.packet.Packet;
import controllers.multiPlayer.utils.FullAddress;
import controllers.multiPlayer.utils.ServerConstants;

import java.io.*;
import java.net.*;

public class PacketHandler {
    private DatagramSocket socket;

    protected void initSocket(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    protected void sendObject(Packet o, FullAddress fullAddress) throws IOException {
        InetAddress address = fullAddress.getInetAddress();
        int desPort = fullAddress.getPort();
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
    }

    protected Packet receiveObject() throws IOException, ClassNotFoundException {

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

    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
