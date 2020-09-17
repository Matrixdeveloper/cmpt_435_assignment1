import java.util.Objects;
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
        int time = rd.nextInt(2);
        System.out.println("sleep " + time);
        while (limit>0) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                System.out.println("Philosopher[" + id + "] >>> " +
                        "Thinking [" + time + " seconds]");
            }
            System.out.println("need fork ");

            // grab forks before eating
            this.RM.getforks(this.id);

            // start eating
            time = rd.nextInt(4) * 1000;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                System.out.println("Philosopher[" + id + "] >>> >>>" +
                        "Eating [" + time + " seconds]");
            }
            // end eating and release forks
            this.RM.relforks(this.id);
            limit--;
        }
    }
}
