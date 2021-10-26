import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public abstract class Slave extends Thread {
    protected final List<Job> waitingJobs = new LinkedList<>();
    protected final int port;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected Socket client;

    public Slave(int port){
        this.port = port;
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
                out.writeObject(current.getId());
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
    protected void connect(){
        try(ServerSocket serverSocket = new ServerSocket(port);) {
            client = serverSocket.accept();
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    protected void disconnect(){
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void doJob(Job current) throws InterruptedException ;

}