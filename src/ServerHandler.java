import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerHandler implements Runnable{
    private final int id;
    private InputStream reader;
    private OutputStream writer;
    private Server server;

    public int getId() {
        return id;
    }

    public ServerHandler(Socket client, int id, Server server) {
        this.id = id;
        this.server = server;
        try {
            reader = client.getInputStream();
            writer = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message.getBytes(StandardCharsets.UTF_8));
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            short bytes;
            while ((bytes = (short) reader.read(buffer)) != -1) {
                server.broadcastMessage(id, id + ": " + new String(buffer, 0, bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
