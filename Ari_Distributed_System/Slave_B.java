public class Slave_B extends Slave {

    public static void main(String[] args) {
        Slave s = new Slave_B(6789);
        s.run();
    }


    public Slave_B(int port){
        super(port);
    }

    @Override
    protected void doJob(JobType current) throws InterruptedException {
        if(current == JobType.JOB_B){
            for (int i = 2; i > 0; i--) {
                System.out.println("Slave B sleeping for " + i + " more seconds");
                Thread.sleep(1_000);
            }

        }
        else{
            for (int i = 10; i > 0; i--) {
                System.out.println("Slave B sleeping for " + i + " more seconds");
                Thread.sleep(1_000);
            }
        }
    }
}
