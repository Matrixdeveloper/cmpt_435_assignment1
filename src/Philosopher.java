import java.util.Random;

public class Philosopher implements Runnable {
    DiningRoom RM;
    int id;
    int limit;

    Philosopher(int id, DiningRoom RM){
        this.id = id;
        this.RM = RM;
        this.limit = 10;
    }

    @Override
    public void run() {

        Random rd = new Random();
        int time = rd.nextInt(2)*1000;
        while (limit>0) {
            try {
                System.out.println("Philosopher[" + id + "] >>> " +
                        "Thinking [" + time + " seconds]");
                Thread.sleep(time);
            } catch (InterruptedException ignored) {}

            // grab forks before eating
            this.RM.getforks(this.id);

            // start eating
            time = rd.nextInt(2)*1000;
            try {
                System.out.println("Philosopher[" + id + "] >>> >>> >>>" +
                        "Eating [" + time + " seconds]");
                Thread.sleep(time);
            } catch (InterruptedException ignored) {}
            // end eating and release forks
            this.RM.relforks(this.id);
            limit--;
        }
    }
}
