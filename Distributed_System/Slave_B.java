public class Slave_B extends Slave {

    public Slave_B(int port){
        super(port);
    }

    @Override
    protected void doJob(Job current) throws InterruptedException {
        if(current.getJobType() == JobType.TYPE_B){
            sleep(2_000);
        }
        else{
            sleep(10_000);
        }
    }
}
