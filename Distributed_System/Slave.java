import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Slave extends Thread {
    public static final int JOB_TIME = 10_000;

    private final List<Job> waitingJobs = new LinkedList<>();
    private final int port;
    private final JobType specialty;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket client;

    public Slave(int port, JobType type){
        this.port = port;
        this.specialty = type;
    }

    @Override
    public void run(){
        try{
            connect();
            while(true){
                if(waitingJobs.isEmpty()){
                    waitingJobs.add((Job) in.readObject()); //block until we have a job to do
                }

                Job current = waitingJobs.remove(0);
                if(current == null)
                    break; //this is the signal that we are done

                doJob(current);

                //Inform the master that we just finished a job
                out.writeObject(current);
            }
            disconnect();
        }
        catch (IOException | ClassNotFoundException | InterruptedException e){
            disconnect();
            e.printStackTrace();
        }
    }


    /**
     * This method connects to the master and sets up the input and output streams
     * that will be used to communicate with it.
     */
    private void connect(){
        try(ServerSocket serverSocket = new ServerSocket(port);) {
            client = serverSocket.accept();
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doJob(Job current) throws InterruptedException {
        if(current.getJobType() == this.specialty){
            Thread.currentThread().sleep(JOB_TIME / 5);
        }
        else{
            Thread.currentThread().sleep(JOB_TIME);
        }
    }

}