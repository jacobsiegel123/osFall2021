import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

public class Client {
    private static final int MAX_THREADS = 10; //randomly chosen, could be changed at any time
    private static String clientID = setClientID();

    public static void main(String[] args) throws IOException {
        Thread[] threads = new Thread[MAX_THREADS];
        int port = 12345; //randomly chosen, we should decide on a common port to use
        ServerSocket serverSocket = new ServerSocket(port);
        int jobID = 1;
        //Keep checking threads, if a job has completed, give the thread a new job
        for (int i = 0; i <= threads.length; i++) {
            //if the loop reaches the end, start again (this is an infinite loop)
            if (i == threads.length) {
                i = 0;
            }
            if (threads[i] == null || !threads[i].isAlive()) {
                startJob(threads[i], jobID++, serverSocket);
            }
        }
    }

    private static void startJob(Thread thread, int jobID, ServerSocket serverSocket) {
        JobType jobType;
        if (Math.random() > .5) {
            jobType = JobType.JOB_A;
        } else {
            jobType = JobType.JOB_B;
        }
        String id = getClientID() + "_" + jobID;
        thread = new Job(id, jobType, serverSocket);
        thread.start();
    }

    private static String setClientID() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public static String getClientID() {
        return clientID;
    }
}
