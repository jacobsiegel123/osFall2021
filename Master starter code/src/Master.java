package src;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;



public class Master {
    private static final String HOSTNAME = "127.0.0.1";
    private final ServerSocket server;
    protected final List<ThreadData> clients = Collections.synchronizedList(new ArrayList<ThreadData>());
    protected final List<SlaveData> slaves = Collections.synchronizedList(new ArrayList<SlaveData>());
    private int port = 2000; //where the next thread will run

    //these fields are used to give new threads ID numbers
    private int slaveID = 1;
    private int clientID = 1;


    public static void main(String[] args) {
        try {
            Master m = new Master(args);
            m.runSystem();
        }catch (IOException e){
            System.out.println("Something went wrong on the server end.");
            e.printStackTrace();
        }
    }

    //TODO make a method that gives a slave a job
    public void assignJob(Slave slave){

    }

    public void getJobFromClient(Client client){
        JobType j = client.sendJob();
    }



    public Master(String[] slavePorts) throws IOException {
        server = new ServerSocket(port++);

        //connect to the slaves
        for(int i = 0; i < slavePorts.length; i++){
            connectToSlave(slaveID++, slavePorts[i], i < slavePorts.length/2);
        }
    }



    /**
     * This is the main method of the server. It accepts jobs from clients
     * and distributes them among the slaves.
     */
    public void runSystem(){
        try {
            boolean done = false;
            while (!done) {
                Socket client = server.accept();


                //set up and store all data relating to the new client
                clients.add(new ThreadData(clientID++, client));
                Thread t = new ListenerThread(port++, clients.size() - 1 , this);


                t.start();


            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * This method takes a job and decides which slave it will be assigned to.
     * It then gets the port number that slave is running on and returns it.
     * the CommunicatorThread will use that number to connect to the slave
     * and send it the job.
     * @param j the job that needs to be assigned
     * @return the port number that the slave can be found on
     */
    protected int loadBalance(Job j){
        //TODO


        return -1;
    }


    private void connectToSlave(int id, String s, boolean typeA){
        try {

            //connect to the slave
            Socket clientSocket = new Socket(HOSTNAME, Integer.parseInt(s));

            //save the relevent data
            slaves.add(new SlaveData(id, clientSocket, typeA));

            //TODO set up jobCompletionThread here

            //TODO probably call load bal, then give slave the job

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
