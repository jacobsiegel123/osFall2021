public class Client<jobType> {
    private int id;
    private JobType jobType;

    public Client(int id, JobType jobType) {
        this.id = id;
        this.jobType = jobType;
    }

    public int getId() {
        return id;
    }

    public JobType getJobType() {
        return jobType;
    }
}
