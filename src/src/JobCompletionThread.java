import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class listens to a single slave for when it finishes a job
 * one for each slave
 */
public class JobCompletionThread extends Thread {
    private final Master master;
    private final int threadId;
    private final ObjectInputStream in;

    public JobCompletionThread(Master m, int id){
        master = m;
        threadId = id;
        in = m.slaves.get(threadId-1).in;
    }

    @Override
    public void run(){
        try {


            boolean done = false;
            while (!done) {
                int job = (int) in.readObject();
                //TODO tell client we are done
            }



        }catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }
}
