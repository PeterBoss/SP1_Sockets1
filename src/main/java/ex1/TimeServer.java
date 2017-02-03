package ex1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Peter 
 */
public class TimeServer {

    private final String host;
    private final int port;

    public TimeServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startServer() throws IOException {
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress(host, port));

        System.out.println("Server listening on port " + port);

        Socket connection;
        while ((connection = socket.accept()) != null) {
            handleConnection(connection);
            connection.close();
        }
    }

    private void handleConnection(Socket connection) throws IOException {
        OutputStream output = connection.getOutputStream();

        PrintStream writer = new PrintStream(output);
        writer.println(new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").format(new Date()));
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        TimeServer server = new TimeServer("localhost", 8080);

        server.startServer();
    }

}
