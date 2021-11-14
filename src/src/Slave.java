import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public abstract class Slave {
    protected final List<Job> waitingJobs = Collections.synchronizedList(new LinkedList<>()); //new LinkedList<>();
    protected final int port;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected Socket client;

    protected int waitTime = 0;

    public Slave(int port){
        this.port = port;
    }


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

    protected static int getPort(String[] args){
        int port;


        if(args == null || args.length == 0){
            Scanner sc = new Scanner(System.in);
            System.out.println("please enter a port number to use for this slave: ");
            port = Integer.parseInt(sc.nextLine());
        }
        else{
            port = Integer.parseInt(args[0]);
        }

        return port;
    }

    protected abstract void doJob(Job current) throws InterruptedException;
    public abstract int getWaitTime();
    public abstract void incrementWaitTime(JobType j);

}