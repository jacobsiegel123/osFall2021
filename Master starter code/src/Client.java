package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

public class Client {
    private static final int MAX_THREADS = 10; //randomly chosen, could be changed at any time
    private static final String clientID = setClientID();

    /*JobType jobType;

    public Client(){
        jobType = jobMaker();
    }*/

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
        JobType jobType = jobMaker();

        String id = getClientID() + "_" + jobID;
        thread = new Job(id, jobType, serverSocket);
        thread.start();
    }

    private static JobType jobMaker(){
        if (Math.random() > .5) {
            return JobType.TYPE_A;
        } else {
            return JobType.TYPE_B;
        }
    }

    /**
     * I envision that the client comes to the master
     * with a job. Client will use this method to tell
     * master what the job is
     * @return JobType
     */
    public JobType sendJob(){
        return jobMaker();
    }

    private static String setClientID() {
        int leftLimit = 'a';// 97; // letter 'a'
        int rightLimit = 'z'; //22; // letter 'z'
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