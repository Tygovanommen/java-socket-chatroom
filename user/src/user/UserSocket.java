package user;

import gui.screens.Chat;

import java.io.IOException;
import java.net.Socket;

public class UserSocket {

    private final String host;
    private final int port;
    private Socket socket;
    private Thread accessThread;

    /**
     * @param host socket host to connect to
     * @param port socket port to connect to
     */
    public UserSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Establish connection to socket
     */
    public boolean connectSocket(Chat runnable) {
        try {
            // Establish connection to socket server
            System.out.println("Connecting...");
            this.socket = new Socket(this.host, this.port);
            Thread.sleep(1000);
            System.out.println("Connected!");

            this.accessThread = new Thread(runnable);
            this.accessThread.start();

            return true;
        } catch (IOException | InterruptedException ex) {
            System.out.println("Something went wrong while connecting to server");
            return false;
        }
    }

    public Thread getAccessThread() {
        return this.accessThread;
    }

    public Socket getSocket() {
        return this.socket;
    }
}