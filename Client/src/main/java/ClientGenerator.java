import java.io.*;
import java.net.*;

public class ClientGenerator {
    private static final int MAX_CLIENTS = 10_000; //randomly chosen, could be changed at any time

    public static void main(String[] args) {
        Thread[] threads = new Thread[MAX_CLIENTS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = createClient(i);
            threads[i].start();
        }
    }


    private static Client createClient(int i) {
        JobType jobType;
        if (Math.random() > .5) {
            jobType = JobType.JOB_A;
        } else {
            jobType = JobType.JOB_B;
        }
        return new Client(i, jobType);
    }
}
