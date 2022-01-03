import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Master2SlaveA extends Thread {
    public Master2SlaveA() {
    }

    @Override
    public void run() {
        int slaveAPort = 1234; //please coordinate
        Socket slaveASocket;
        try {
            slaveASocket = new Socket("127.0.0.1", slaveAPort);
            PrintWriter outToSlaveA = new PrintWriter(slaveASocket.getOutputStream(), true);
            while (true) {
                System.out.println("M2Sa (maybe) beginning spin");
                while (!Master.slaveAHasNext()) {
                    try {
                        Thread.sleep(1500); //sleeping for some random time to stop incessant polling
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("M2Sa Spin finished or avoided");
                String nextJob = Master.getNextJobSlaveA();
                System.out.println("M2Sa next job is " + nextJob);
                outToSlaveA.println(nextJob);
                System.out.println("M2Sa sent job " + nextJob + ", restarting...");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

