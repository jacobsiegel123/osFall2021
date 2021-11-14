import java.io.*;
import java.net.*;

public class JobThread extends Thread {
    private int id;
    private JobType jobType;
    private ServerSocket serverSocket;

    public JobThread(int id, JobType jobType, ServerSocket serverSocket) {
        this.id = id;
        this.jobType = jobType;
        this.serverSocket = serverSocket;
    }

    public int getJobId() {
        return id;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void run() {
        int masterResponse;
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.print("Setting up the streams " + getId() + "... ::");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("" + getId() + "|" + getJobType());
            if ((masterResponse = in.read()/*expects an int*/) == getJobId()) {
                System.out.println("Received " + masterResponse + " as confirmation that Server finished client number " + getId());
            } else {
                System.out.println("Error, Server response was " + masterResponse + ", job ID is " + getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
