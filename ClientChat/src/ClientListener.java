import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientListener implements Runnable {
    private InputStream inputStream;

    public ClientListener(Socket socket) {
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            short bytes;
            while ((bytes = (short) inputStream.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
