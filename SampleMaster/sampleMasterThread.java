import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class sampleMasterThread extends Thread {
    private final Socket listenToClient;
    int i;

    public sampleMasterThread(Socket listenToClient, int i) {
        this.listenToClient = listenToClient;
        this.i = i;
    }

    @Override
    public void run() {
        String inStr;
        try {
            while (true) {
                System.out.println(i + "Made it here");
                BufferedReader in = new BufferedReader(new InputStreamReader(listenToClient.getInputStream()));
                inStr = in.readLine();
                System.out.println(i + "Line read: " + inStr);
                if (inStr == null) {
                    System.out.println("line read was null, terminating thread...");
                    return;
                }
                int responsePort = Integer.parseInt(inStr.substring(0, inStr.indexOf("|")));
                System.out.println(i + "responsePort: " + responsePort);
                Socket responseSocket = new Socket("127.0.0.1", responsePort);
                PrintWriter outToClient = new PrintWriter(responseSocket.getOutputStream(), true);
                outToClient.println(inStr.substring(inStr.indexOf("|") + 1, inStr.indexOf("||")));
                System.out.println(i + "Finished! Restarting...");
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
