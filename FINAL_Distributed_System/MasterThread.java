import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class MasterThread extends Thread {
    private final Socket listenToClient;

    public MasterThread(Socket listenToClient) {
        this.listenToClient = listenToClient;
    }

    @Override
    public void run() {
        String inStr;
        try {
            while (true) {
                System.out.println("MT made it here");
                BufferedReader in = new BufferedReader(new InputStreamReader(listenToClient.getInputStream()));
                inStr = in.readLine();
                System.out.println("MT Line read: " + inStr);
                if (inStr == null) {
                    System.out.println("MT line read was null, terminating thread...");
                    return;
                } else {
                    LoadBalancer.balanceLoadIntake(inStr);
                }
                System.out.println("MT Finished! Restarting...");
            }
        } catch (SocketException se) {
            System.out.println("Client disconnected");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
