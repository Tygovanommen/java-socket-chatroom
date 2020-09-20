package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final int port;
    private final List<Receiver> allThreads = new ArrayList<>();

    /**
     * @param port port the socket should connect to.
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * Start socket server
     */
    public void startServer() {
        try {
            // Create socket connection
            ServerSocket socketServer = new ServerSocket(this.port);
            acceptUsers(socketServer);
        } catch (IOException e) {
            System.err.println("Could not connect to port: " + this.port);
            System.exit(1);
        }
    }

    /**
     * Grant access to new users
     *
     * @param serverSocket current server socket connection
     */
    private void acceptUsers(ServerSocket serverSocket) {
        // Show current socket address information
        System.out.println("Server port: " + serverSocket.getLocalSocketAddress());
        while (true) {
            try {
                // Accept new user
                Socket socket = serverSocket.accept();
                System.out.println("New user connected: " + socket.getRemoteSocketAddress());

                // Start new user thread
                Receiver user = new Receiver(this, socket);
                Thread thread = new Thread(user);
                thread.start();

                // Add user to User list
                this.allThreads.add(user);
            } catch (IOException ex) {
                System.out.println("Failed accepting new user on port: " + this.port);
            }
        }
    }

    public List<Receiver> getThreadsByRoom(String room) {
        List<Receiver> threads = new ArrayList<>();
        for (Receiver receiver : this.allThreads) {
            if (receiver.getRoom().equals(room)) {
                threads.add(receiver);
            }
        }
        return threads;
    }
}