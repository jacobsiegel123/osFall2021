import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class sampleMaster {
    public static void main(String[] args) {
        Socket clientSocket = null;
        ServerSocket listenForClient = null;
        int index = 0;
        try {
            listenForClient = new ServerSocket(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                System.out.println("Started Again");
                clientSocket = listenForClient.accept();
            } catch (IOException e) {
                System.out.println(e);
            }
            new sampleMasterThread(clientSocket, index++).start();
        }
    }
}
