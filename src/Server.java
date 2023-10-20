import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final short PORT = 1906;
    private final List<ServerHandler> serverHandlers;
    private ServerSocket serverSocket;

    public Server() {
        serverHandlers = new ArrayList<>();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("***** Server began with port is 1906 *****");
        } catch (IOException e) {
            e.printStackTrace();
            endServer();
        }
    }

    public void serverWorking() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Client IP: " + client.getInetAddress() + " connected to server");
                ServerHandler serverHandler = new ServerHandler(client, (int) System.currentTimeMillis(), this);
                serverHandlers.add(serverHandler);
                new Thread(serverHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMessage(int id, String message)  {
        for (ServerHandler x : serverHandlers) {
            if (id != x.getId())
                x.sendMessage(message);
        }
    }

    public void endServer() {
        try {
            serverSocket.close();
            System.out.println("!!!!! Server finished !!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
        server.serverWorking();
    }
}