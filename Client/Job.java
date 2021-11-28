import java.io.*;
import java.net.*;

public class Job extends Thread {
    private int id;
    private JobType jobType;
    private Socket masterSocket;
    private int responsePort;
    public int threadID;

    public Job(int id, JobType jobType, Socket masterSocket, int responsePort, int threadID) {
        this.id = id;
        this.jobType = jobType;
        this.masterSocket = masterSocket;
        this.responsePort = responsePort;
        this.threadID = threadID;
    }

    public int getJobId() {
        return id;
    }

    public int getResponsePort() {
        return responsePort;
    }

    public int getThreadID() {
        return threadID;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void run() {
        int masterResponse;
        try {
            System.out.println("ThreadID: " + threadID + ", JobID: " + getJobId() + "Made it here");
            ServerSocket listenForCompletion = new ServerSocket(getResponsePort() + getThreadID());
            System.out.println("Setting up out stream " + "ThreadID: " + getThreadID() + ", JobID: " + getJobId());
            PrintWriter outToMaster = new PrintWriter(masterSocket.getOutputStream(), true);
            outToMaster.println((getResponsePort() + getThreadID()) + "|" + getJobId() + "||" + getJobType());
            System.out.println("Sent out: " + (getResponsePort() + getThreadID()) + "|" + getJobId() + "||" + getJobType());
            Socket listenSocket = listenForCompletion.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(listenSocket.getInputStream()));
            System.out.println("ThreadID: " + getThreadID() + ", JobID: " + getJobId() + "waiting for response...");
            masterResponse = Integer.parseInt(in.readLine()); /*expects the ID as a String*/
            if (masterResponse == getJobId()) {
                System.out.println("Received " + masterResponse + " on port " + getResponsePort() + " as confirmation that Server finished job number " + getJobId());
            } else {
                System.out.println("Error, Server response was " + masterResponse + ", job ID is " + getJobId());
            }
            listenForCompletion.close();
            Socket notifyMainClient = new Socket("127.0.0.1", (getResponsePort() - 1));
            System.out.println(getThreadID() + "Connected to mainClient");
            PrintWriter outToMainClient = new PrintWriter(notifyMainClient.getOutputStream(), true);
            outToMainClient.println(getThreadID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}