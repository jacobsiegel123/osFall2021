import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Client {
    static Scanner scanner = new Scanner(System.in);
    static String input;
    static int jobID = 1;
    static Random r = new Random();
    static int responsePort = setResponsePort();

    private static int setResponsePort() {
        int testPortNumber = getRandom4DigitNum();
        while (!isPortAvailable(testPortNumber)) {
            testPortNumber = getRandom4DigitNum();
        }
        return testPortNumber;
    }

    private static int getRandom4DigitNum() {
        return r.nextInt(9000) + 1000;
    }

    private static boolean isPortAvailable(int testPort) {
        try {
            ServerSocket testSocket = new ServerSocket(testPort);
            testSocket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        new ListenForJobCompletion().start();
        int masterPort = 12345; //randomly chosen, we should decide on a common port to use
        Socket masterSocket = new Socket("127.0.0.1", masterPort);
        while (true) {
            input = scanner.nextLine().toUpperCase();
            while (!(input.equals("A") || input.equals("B"))) {
                System.out.println("Invalid job type, enter \"A\" or \"B\"");
                input = scanner.nextLine().toUpperCase();
            }
            new Job(input, masterSocket).start();
        }
    }

    public static synchronized int getJobID() {
        return jobID++;
    }

    public static int getResponsePort() {
        return responsePort;
    }

}
