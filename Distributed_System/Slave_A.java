public class Slave_A extends Slave {

    public Slave_A(int port){
        super(port);
    }

    @Override
    protected void doJob(Job current) throws InterruptedException {
        if(current.getJobType() == JobType.TYPE_A){
            sleep(2_000);
        }
        else{
            sleep(10_000);
        }
    }
}
