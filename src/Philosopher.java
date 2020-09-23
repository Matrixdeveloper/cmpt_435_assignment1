//CMPT435 2020 Fall term
//Name: Yue Weng
//Student ID: 1121 9127
//NSID: yuw857


import java.util.Random;

public class Philosopher implements Runnable {
    DiningRoomM RM;
    int id;
    int rounds;
    int timeBound;

    /**
     * constructor of Philosopher
     * @param id int, identification of Philosopher
     * @param RM Class DiningRoom, the DiningRoom where Philosopher will seat
     * @param rounds int, limit of Philosopher eating tries
     */
    Philosopher(int id, DiningRoomM RM, int rounds ,int timeBound){
        this.id = id;
        this.RM = RM;
        this.rounds = rounds;
        this.timeBound = timeBound;
    }

    @Override
    public void run() {
        Random rd = new Random();
        int time = rd.nextInt(timeBound)*1000;
        while (rounds>0) {
            // thinking some random time
            try {
                System.out.println("Philosopher[" + id + "] >>> " +
                        "Thinking [" + time/1000 + " seconds]");
                Thread.sleep(time);
            } catch (InterruptedException esl) {
                System.out.println("%%% Philosopher["+id+"] thinking interrupted %%%");
            }

            // grab forks before eating
            this.RM.getforks(this.id);

            // start eating
            time = rd.nextInt(timeBound)*1000;
            try {
                System.out.println("Philosopher[" + id + "] >>> >>> >>>" +
                        "Eating [" + time/1000 + " seconds]");
                Thread.sleep(time);
            } catch (InterruptedException esl) {
                System.out.println("%%% Philosopher["+id+"] eating interrupted %%%");
            }

            // end eating and release forks
            this.RM.relforks(this.id);
            rounds--;
        }
        // finish all round, say something and leave
        System.out.println(">>>>> Philosopher["+id+"] FULL <<<<<");
    }
}
