public class Slave_A extends Slave {

    public static void main(String[] args) {
        Slave s = new Slave_A(1234);
        s.run();
    }

    public Slave_A(int port){
        super(port);
    }

    @Override
    protected void doJob(JobType current) throws InterruptedException {
        if(current == JobType.JOB_A){
            for (int i = 2; i > 0; i--) {
                System.out.println("Slave A sleeping for " + i + " more seconds");
                Thread.sleep(1_000);
            }

        }
        else{
            for (int i = 10; i > 0; i--) {
                System.out.println("Slave A sleeping for " + i + " more seconds");
                Thread.sleep(1_000);
            }
        }
    }

}
