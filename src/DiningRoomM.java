//CMPT435 2020 Fall term
//Name: Yue Weng
//Student ID: 1121 9127
//NSID: yuw857


import java.util.Arrays;

public class DiningRoomM {
    int[] forkStatus; // invariant: initially all fork are 1 <available>
    int numPhilos; // record number of philosophers seat in that room

    DiningRoomM(){
        this.numPhilos = 5;
        this.forkStatus = new int[this.numPhilos];
        Arrays.fill(forkStatus, 1);
    }


    /**
     * Private function for calculating which forks should be
     * the left and right grabbed by Philosopher with id
     * @param id int, id of philosopher
     * @return int array[2], index[0] is the left fork index
     * index[1] is the right fork index
     */
    private int[] whichTwoForks(int id)
    {
        // calculate target forks
        int lf, rf;
        lf = id - 2;
        rf = id % numPhilos;
        if(lf<0) lf = numPhilos-1;
        // from id=1 to n-1, philosopher pick left then right
        if(id==numPhilos){
            // solving the deadlock of circular wait
            // let largest ID philosopher pick right then left
            // switch left right fork index
            lf=lf+rf;
            rf=lf-rf;
            lf=lf-rf;
        }
        return new int[]{lf,rf};
    }


    /**
     * Every philosopher should provide their id to grab forks
     * case 1: If that's the turn and both two forks are available,
     * the philosopher will pick two forks and continue do their part.
     * case 2: If not the turn or forks are not available, the philosopher
     * will being blocked until the condition is fulfilled.
     * @param id int, identification of request philosopher
     */
    public synchronized void getforks(int id)
    {
        // implicit queue waiting for lock
        int[] twoFork = whichTwoForks(id);
        try {
            // try grab a fork;if fail, wait on fork cv
            while (forkStatus[twoFork[0]] == 0){
                wait();
            }
            // update fork status
            forkStatus[twoFork[0]]=0;
            while (forkStatus[twoFork[1]] == 0){
                wait();
            }
            forkStatus[twoFork[1]]=0;
        }catch (InterruptedException ef){
            System.out.println("%%% Exception: Philosopher ["+id+"] grab" +
                    " fork fail %%% ");
        }
    }

    public synchronized void relforks(int id)
    {
        // find the fork
        int[] twoFork = whichTwoForks(id);
        // release the fork
        forkStatus[twoFork[0]] = 1;
        forkStatus[twoFork[1]] = 1;
        // wake up other to check
        notifyAll();
    }


}
