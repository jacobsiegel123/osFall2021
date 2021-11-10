import java.util.Scanner;

public class Slave_A extends Slave {

    public static void main(String[] args) {
        Slave s = new Slave_A(getPort(args));
        s.run();
    }

    public Slave_A(int port){
        super(port);
    }

    @Override
    protected void doJob(Job current) throws InterruptedException {
        if(current.getJobType() == JobType.TYPE_A){
            Thread.currentThread().sleep(2_000);
        }
        else{
            Thread.currentThread().sleep(10_000);
        }
    }

}
