import java.util.Random;

public class Philosopher implements Runnable {
    DiningRoom RM;
    int id;
    int rounds;

    Philosopher(int id, DiningRoom RM, int rounds){
        this.id = id;
        this.RM = RM;
        this.rounds = rounds;
    }

    @Override
    public void run() {
        Random rd = new Random();
        int time = rd.nextInt(5)*1000;
        while (rounds>0) {
            try {
                System.out.println("Philosopher[" + id + "] >>> " +
                        "Thinking [" + time/1000 + " seconds]");
                Thread.sleep(time);
            } catch (InterruptedException ignored) {}

            // grab forks before eating
            this.RM.getforks(this.id);

            // start eating
            time = rd.nextInt(5)*1000;
            try {
                System.out.println("Philosopher[" + id + "] >>> >>> >>>" +
                        "Eating [" + time/1000 + " seconds]");
                Thread.sleep(time);
            } catch (InterruptedException ignored) {}
            // end eating and release forks
            this.RM.relforks(this.id);
            rounds--;
        }
        System.out.println(">>>>> Philosopher["+id+"] FULL <<<<<");
    }
}
