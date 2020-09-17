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
    Lock gate;
    Condition[] forks;
    int[] forkStatus; // invariant
    int numPhilos;

    DiningRoom()
    {
        this.gate = new ReentrantLock();
        this.numPhilos = 5;
        this.forks = new Condition[this.numPhilos];
        this.forkStatus = new int[this.numPhilos];
        Arrays.setAll(forks, i->gate.newCondition());
        Arrays.fill(forkStatus, 1);
    }

    private int[] enter(int id)
    {
        // calculate target forks
        int lf, rf;
        lf = id - 2;
        rf = id % numPhilos;
        if(lf<0) lf = numPhilos-1;
        // from id=1 to n-1, philosopher pick left then right
        if(id==numPhilos){
            // let largest ID philosopher pick right then left
            // switch left right
            lf=lf+rf;
            rf=lf-rf;
            lf=lf-rf;
        }
        return new int[]{lf,rf};
    }


    public void getforks(int id) {
        gate.lock();
        int[] twoforks = enter(id);
        try {
            while (forkStatus[twoforks[0]] == 0){
                forks[twoforks[0]].await();
            }
            forkStatus[twoforks[0]]=0;
            while (forkStatus[twoforks[1]] == 0){
                forks[twoforks[1]].await();
            }
            forkStatus[twoforks[1]]=0;
        }catch (InterruptedException ignored){}
        finally {
            gate.unlock();
        }

    }

    public void relforks ( int id)
    {
        gate.lock();
        int[] twoforks = enter(id);
        forkStatus[twoforks[0]] = 1;
        forkStatus[twoforks[1]] = 1;
        forks[twoforks[0]].signal();
        forks[twoforks[1]].signal();
        gate.unlock();
    }
}

