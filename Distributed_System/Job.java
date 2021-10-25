import java.util.Random;

public class Job implements java.io.Serializable {
    private static int id_to_assign = 1;
    private static final Random generator = new Random();
    private final JobType type;
    private final int id;

    public Job(){
        id = id_to_assign++;
        type = (generator.nextBoolean() ? JobType.TYPE_A : JobType.TYPE_B);
    }

    public Job(JobType type){
        this.id = id_to_assign++;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public JobType getJobType(){return type;}
}
