package src;

public class Slave_B extends Slave {

    public static void main(String[] args) {
        Slave s = new Slave_B(getPort(args));
        s.run();
    }


    public Slave_B(int port){
        super(port);
    }

    @Override
    protected void doJob(Job current) throws InterruptedException {
        if(current.getJobType() == JobType.TYPE_B){
            Thread.currentThread().sleep(2_000);
            waitTime -= 2;
        }
        else{
            Thread.currentThread().sleep(10_000);
            waitTime -= 10;
        }
    }

    @Override
    public int getWaitTime() {
        return waitTime;
    }

    @Override
    public void incrementWaitTime(JobType j) {
        if (j == JobType.TYPE_B){
            waitTime += 2;
        } else {
            waitTime += 10;
        }
    }
}
