import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int MAX_THREADS = 10; //randomly chosen, could be changed at any time
    private static Thread[] threads = new Thread[MAX_THREADS];

    public static void main(String[] args) throws IOException {
        int jobID = 0;
        int masterPort = 12345; //randomly chosen, we should decide on a common port to use
        int responsePort;
        Socket masterSocket = new Socket("127.0.0.1", masterPort);
        if (args.length != 0) {
        responsePort = Integer.parseInt(args[0]);}
        else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the first response port number: ");
            responsePort = sc.nextInt();
        }
        //Starts off the threads
        for (int i = 0; i < threads.length; i++) {
            startJob(jobID++, masterSocket, responsePort, i);
        }
        System.out.println("Finished For loop");
        //Waits for a thread to complete, then gives that thread a new job
        ServerSocket listenSocket = new ServerSocket(responsePort - 1);
        for (int i = 0; i < 100; i++) {
            Socket listenToThreads = listenSocket.accept();
            BufferedReader inFromThreads = new BufferedReader(new InputStreamReader(listenToThreads.getInputStream()));
            startJob(jobID++, masterSocket, responsePort, Integer.parseInt(inFromThreads.readLine()));
        }
        listenSocket.close();
        masterSocket.close();
        System.exit(0);
    }

    private static void startJob(int jobID, Socket masterSocket, int responsePort, int i) {
        JobType jobType;
        if (Math.random() > .5) {
            jobType = JobType.JOB_A;
        } else {
            jobType = JobType.JOB_B;
        }
        threads[i] = new Job(jobID, jobType, masterSocket, responsePort, i);
        threads[i].start();
        System.out.println("Started thread " + i);
    }
}
