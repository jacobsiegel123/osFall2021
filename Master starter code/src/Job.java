package src;

import java.io.*;
import java.net.*;

public class Job extends Thread {
    private String id;
    private JobType jobType;
    private ServerSocket serverSocket;

    public Job(String id, JobType jobType, ServerSocket serverSocket) {
        this.id = id;
        this.jobType = jobType;
        this.serverSocket = serverSocket;
    }

    public int getJobId() {
        return Integer.parseInt(id.substring(id.indexOf("_" + 1)));
    }

    public String getFullId() {
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
            out.println(getFullId() + "|" + getJobType());
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