public class LoadBalancer extends Thread {
    private static long timeUntilSlaveAIsIdle;
    private static long timeUntilSlaveBIsIdle;
    public LoadBalancer() {}

    @Override
    public void run() {
        while (true) {
            System.out.println("LB (maybe) beginning spin");
            while (Master.unsortedJobs.size() == 0) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("LB Spin finished or avoided");
            String jobStr = Master.unsortedJobs.poll();
            JobType job = JobType.valueOf(jobStr.substring(jobStr.indexOf("||") + 2));
            long timeUntilSlaveAFinishesJob = getTimeUntilSlaveAFinishesJob(job);
            long timeUntilSlaveBFinishesJob = getTimeUntilSlaveBFinishesJob(job);
            if (timeUntilSlaveAFinishesJob < timeUntilSlaveBFinishesJob) {
                System.out.println("timeUntilSlaveAFinishesJob: " + timeUntilSlaveAFinishesJob + ", timeUntilSlaveBFinishesJob: " + timeUntilSlaveBFinishesJob);
                System.out.println("Adding \"" + jobStr + "\" to slave A");
                Master.slaveAJobs.add(jobStr);
                timeUntilSlaveAIsIdle = timeUntilSlaveAFinishesJob;
            } else {
                System.out.println("timeUntilSlaveAFinishesJob: " + timeUntilSlaveAFinishesJob + ", timeUntilSlaveBFinishesJob: " + timeUntilSlaveBFinishesJob);
                System.out.println("Adding \"" + jobStr + "\" to slave B");
                Master.slaveBJobs.add(jobStr);
                timeUntilSlaveBIsIdle = timeUntilSlaveBFinishesJob;
            }
            System.out.println("LB Finished! Restarting...");
        }
    }

    public static synchronized void balanceLoadIntake(String jobStr) {
        Master.unsortedJobs.add(jobStr);
    }

    private static long getTimeUntilSlaveAFinishesJob(JobType job) {
        long jobTime;
        if (job == JobType.JOB_A) {
            jobTime = 2000;
        }
        else {
            jobTime = 10_000;
        }
        if (System.currentTimeMillis() > timeUntilSlaveAIsIdle) {
            return System.currentTimeMillis() + jobTime;
        }
        else {
            return timeUntilSlaveAIsIdle + jobTime;
        }
    }

    private static long getTimeUntilSlaveBFinishesJob(JobType job) {
        long jobTime;
        if (job == JobType.JOB_B) {
            jobTime = 2000;
        }
        else {
            jobTime = 10_000;
        }
        if (System.currentTimeMillis() > timeUntilSlaveBIsIdle) {
            return System.currentTimeMillis() + jobTime;
        }
        else {
            return timeUntilSlaveBIsIdle + jobTime;
        }
    }
}
