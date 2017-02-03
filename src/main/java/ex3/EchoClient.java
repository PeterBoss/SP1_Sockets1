package ex3;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * A client which connects to a server.
 */
public class EchoClient {

    private final String host;
    private final int port;
    private Socket clientSocket;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void open() throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port));
        System.out.println("Client connected to server on port " + port);
    }

    /**
     * Sends a message to the server by opening a socket, writing to the input and reading from the output.
     *
     * @param message The message to send
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        // Write to the server
        OutputStream output = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output);
        writer.println(message);
        writer.flush();
    }

    /**
     * Reads a message from the server, if connected.
     *
     * @return A message from the server.
     * @throws IOException
     */
    public String readMessage() throws IOException {
        // Read from the server
        InputStream input = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String fromServer;
        while ((fromServer = reader.readLine()) == null) {
            // Wait until the server says something interesting
        }
        return fromServer;
    }

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient("localhost", 8080);
        client.open();
        client.sendMessage("LOWERTEST");
        String message = client.readMessage();
        System.out.println(message);
    }

}
