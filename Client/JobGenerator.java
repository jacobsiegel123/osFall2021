import java.io.IOException;
import java.net.ServerSocket;

public class JobGenerator {
    private static final int MAX_CLIENTS = 10; //randomly chosen, could be changed at any time

    public static void main(String[] args) throws IOException {
        Thread[] threads = new Thread[MAX_CLIENTS];
        int port = 12345; //randomly chosen, we should decide on a common port to use
        ServerSocket serverSocket = new ServerSocket(port);
        int id = 1;
        //start off all threads
        for (Thread thread : threads) {
            startJob(thread, id++, serverSocket);
        }
        //Keep checking threads, if a job has completed, give the thread a new job
        for (int i = 0; i <= threads.length; i++) {
            //if the loop reaches the end, start again (this is an infinite loop)
            if (i == threads.length) {
                i = 0;
            }
            if (threads[i] == null || !threads[i].isAlive()) {
                startJob(threads[i], id++, serverSocket);
            }
        }
    }

    private static void startJob(Thread thread, int id, ServerSocket serverSocket) {
        JobType jobType;
        if (Math.random() > .5) {
            jobType = JobType.JOB_A;
        } else {
            jobType = JobType.JOB_B;
        }
        thread = new Job(id, jobType, serverSocket);
        thread.start();
    }
}
