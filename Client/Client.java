import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Client {
    private static final int MAX_THREADS = 10; //randomly chosen, could be changed at any time

    public static void main(String[] args) throws IOException {
        Thread[] threads = new Thread[MAX_THREADS];
        int masterPort = 12345; //randomly chosen, we should decide on a common port to use
        int responsePort;
        if (args[0] != null) {
        responsePort = Integer.parseInt(args[0]);}
        else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the first response port number: ");
            responsePort = sc.nextInt();
        }
        ServerSocket serverSocket = new ServerSocket(masterPort);
        int jobID = 1;
        //Keep checking threads, if a job has completed, give the thread a new job
        for (int i = 0; i <= threads.length; i++) {
            //if the loop reaches the end, start again (this is an infinite loop)
            if (i == threads.length) {
                i = 0;
            }
            if (threads[i] == null || !threads[i].isAlive()) {
                startJob(threads[i], jobID++, serverSocket, responsePort + i);
            }
        }
    }

    private static void startJob(Thread thread, int jobID, ServerSocket serverSocket, int responsePort) {
        JobType jobType;
        if (Math.random() > .5) {
            jobType = JobType.JOB_A;
        } else {
            jobType = JobType.JOB_B;
        }
        thread = new Job(jobID, jobType, serverSocket, responsePort);
        thread.start();
    }
}
