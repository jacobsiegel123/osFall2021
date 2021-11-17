package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//listens to client for a job and passes it to slave
//one for each client
public class ListenerThread extends Thread {
    private static final String HOST_NAME = "127.0.0.1";
    private final Socket socket;
    private final ObjectInputStream clientIn;
    private final ObjectOutputStream clientOut;
    private final Socket slaveSocket;
    private final ObjectInputStream slaveIn;
    private final ObjectOutputStream slaveOut;

    public ListenerThread(int port, int index, Master m) throws IOException {
        ThreadData cd = m.clients.get(index);
        this.socket = cd.socket;
        this.clientIn = cd.in;
        this.clientOut = cd.out;
        this.slaveSocket = new Socket(HOST_NAME, port);
        this.slaveIn = new ObjectInputStream(slaveSocket.getInputStream());
        this.slaveOut = new ObjectOutputStream(slaveSocket.getOutputStream());
    }

    @Override
    public void run(){
        try {
            while (true) {
                Job j = (Job) clientIn.readObject();
                //TODO call load balancer and find out which slave gets this job

                //TODO send this job to the correct slave
            }
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
