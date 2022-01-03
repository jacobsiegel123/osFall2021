import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Master {
    static final Queue<String> unsortedJobs = new LinkedList<>();
    static final Queue<String> slaveAJobs = new LinkedList<>();
    static final Queue<String> slaveBJobs = new LinkedList<>();

    public static void main(String[] args) {
        new LoadBalancer().start();
        new Master2SlaveA().start();
        new Master2SlaveB().start();
        new Slaves2ClientMaster().start();
        Socket clientSocket = null;
        ServerSocket listenForClient = null;
        try {
            listenForClient = new ServerSocket(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                System.out.println("Master Started Again");
                clientSocket = listenForClient.accept();
            } catch (IOException e) {
                System.out.println(e);
            }
            new MasterThread(clientSocket).start();
        }
    }

    public static String getNextJobSlaveA() {
        return slaveAJobs.poll();
    }

    public static String getNextJobSlaveB() {
        return slaveBJobs.poll();
    }

    public static boolean slaveAHasNext() {
        return !slaveAJobs.isEmpty();
    }

    public static boolean slaveBHasNext() {
        return !slaveBJobs.isEmpty();
    }
}
