//CMPT435 2020 Fall term
//Name: Yue Weng
//Student ID: 1121 9127
//NSID: yuw857


import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DiningRoom
{
    Lock gate; // reentrantLock for monitor
    Condition[] forks; // condition variable for each fork
    int[] forkStatus; // invariant: initially all fork are 1 <available>
    int numPhilos; // record number of philosophers seat in that room

    /**
     *  constructor of DiningRoom
     */
    DiningRoom()
    {
        this.gate = new ReentrantLock();
        this.numPhilos = 5;
        this.forks = new Condition[this.numPhilos];
        this.forkStatus = new int[this.numPhilos];
        // initialize CV
        Arrays.setAll(forks, i->gate.newCondition());
        // initialize forks invariant
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
    public void getforks(int id) {
        // implicit queue waiting for lock
        gate.lock();
        int[] twoFork = whichTwoForks(id);
        try {
            // try grab a fork;if fail, wait on fork cv
            while (forkStatus[twoFork[0]] == 0){
                forks[twoFork[0]].await();
            }
            // update fork status
            forkStatus[twoFork[0]]=0;
            while (forkStatus[twoFork[1]] == 0){
                forks[twoFork[1]].await();
            }
            forkStatus[twoFork[1]]=0;
        }catch (InterruptedException ef){
            System.out.println("%%% Exception: Philosopher ["+id+"] grab" +
                    " fork fail %%% ");
        }
        finally {
            // must release lock
            gate.unlock();
        }

    }

    public void relforks ( int id)
    {
        gate.lock();
        int[] twoFork = whichTwoForks(id);
        // set fork status to available
        forkStatus[twoFork[0]] = 1;
        forkStatus[twoFork[1]] = 1;
        // release fork in order
        forks[twoFork[0]].signal();
        forks[twoFork[1]].signal();
        gate.unlock();
    }
}

