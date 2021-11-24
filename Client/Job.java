import java.io.*;
import java.net.*;

public class Job extends Thread {
    private int id;
    private JobType jobType;
    private ServerSocket serverSocket;
    private int responsePort;

    public Job(int id, JobType jobType, ServerSocket serverSocket, int responsePort) {
        this.id = id;
        this.jobType = jobType;
        this.serverSocket = serverSocket;
        this.responsePort = responsePort;
    }

    public int getJobId() {
        return id;
    }

    public int getResponsePort() {
        return responsePort;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void run() {
        int masterResponse;
        try {
            ServerSocket responseSocket = new ServerSocket(responsePort);
            Socket clientSocket = serverSocket.accept();
            Socket responseClient = responseSocket.accept();
            System.out.print("Setting up the streams " + getId() + "... ::");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(responseClient.getInputStream()));
            out.println(getResponsePort() + "|" + getJobId() + "||" + getJobType());
            if ((masterResponse = in.read()/*expects an int*/) == getJobId()) {
                System.out.println("Received " + masterResponse + " as confirmation that Server finished client number " + getJobId());
            } else {
                System.out.println("Error, Server response was " + masterResponse + ", job ID is " + getJobId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}