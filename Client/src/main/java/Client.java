import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private int id;
    private JobType jobType;

    public Client(int id, JobType jobType) {
        this.id = id;
        this.jobType = jobType;
    }

    public int getJobId() {
        return id;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void run() {
        int port = 12345; //randomly chosen, we should decide on a common port to use
        int serverResponse;
        try (Socket clientSocket = new Socket("127.0.0.1", port)
        ) {
            System.out.println("Setting up the streams...");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("" + getId() + "|" + getJobType());
            if ((serverResponse = in.read() /*expects an int*/) == getJobId()) {
                System.out.println("Received " + serverResponse + " as confirmation that Server finished client number " + getId());
            }
            else {
                System.out.println("Error, Server response was " + serverResponse + ", job ID is " + getId());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
