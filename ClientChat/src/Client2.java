import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public Client2() {
        try {
            Socket socket = new Socket("localhost", Client.PORT);
            System.out.println("Connected to server successfully");

            ClientListener listener = new ClientListener(socket);
            new Thread(listener).start();
            OutputStream outputStream = socket.getOutputStream();
            Scanner sc = new Scanner(System.in);
            while (true){
                outputStream.write(sc.nextLine().getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client2();
    }
}
