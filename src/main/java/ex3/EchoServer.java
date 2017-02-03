package ex3;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A server which simply just echoes whatever it receives
 */
public class EchoServer {

    private final String host;
    private final int port;

    public EchoServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Starts running the server.
     *
     * @throws IOException If network or I/O or something goes wrong.
     */
    public void startServer() throws IOException {
        // Create a new unbound socket
        ServerSocket socket = new ServerSocket();
        // Bind to a port number
        socket.bind(new InetSocketAddress(host, port));

        System.out.println("Server listening on port " + port);

        // Wait for a connection
        Socket connection;
        while ((connection = socket.accept()) != null) {
            // Handle the connection in the #handleConnection method below
            handleConnection(connection);
            // Now the connection has been handled and we've sent our reply
            // -- So now the connection can be closed so we can open more
            //    sockets in the future
            connection.close();
        }
    }

    /**
     * Handles a connection from a client by simply echoing back the same thing
     * the client sent.
     *
     * @param connection The Socket connection which is connected to the client.
     * @throws IOException If network or I/O or something goes wrong.
     */
    private void handleConnection(Socket connection) throws IOException {
        OutputStream output = connection.getOutputStream();
        InputStream input = connection.getInputStream();

        // Read whatever comes in
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();
        
        String response = handleInput(line);
        
        // Print the same line we read to the client
        PrintStream writer = new PrintStream(output);
        writer.println(response);
    }

    private String handleInput(String raw) {

        String response = "";
        try {
            String[] words = raw.split("#");

            String prefix = words[0];
            String message = words[1];

            switch (prefix) {
                case "UPPER":
                    response = message.toUpperCase();
                    break;
                case "LOWER":
                    response = message.toLowerCase();
                    break;
                case "REVERSE":
                    response = new StringBuilder(message).reverse().toString();
                    break;
                case "TRANSLATE":
                    response = "dog";
                    break;
                default:
                    //close connection
                    break;
            }
        } catch (Exception e) {
            response = raw;
        }
        return response;
    }

    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer("localhost", 8080);

        // This method will block, forever!
        server.startServer();
    }

}
