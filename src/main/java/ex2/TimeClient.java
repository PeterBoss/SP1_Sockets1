package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author PeterBoss
 */
public class TimeClient {

    private final String host;
    private final int port;
    private Socket clientSocket;

    public TimeClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void open() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port));
        System.out.println("Client connected to server on port " + port);
    }

    public String readMessage() throws IOException {
        InputStream input = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String fromServer;
        while ((fromServer = reader.readLine()) == null) {
        }
        return fromServer;
    }

    public static void main(String[] args) throws IOException {
        TimeClient client = new TimeClient("localhost", 8080);
        client.open();
        String message = client.readMessage();
        System.out.println(message);
    }

}
