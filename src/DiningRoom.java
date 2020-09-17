import java.util.Arrays;
import java.util.concurrent.Semaphore;


public class DiningRoom {
    Semaphore[] forks;
    int numPhilos;
    int turn;

    DiningRoom(){
        numPhilos = 5;
        forks = new Semaphore[numPhilos];
        Arrays.setAll(forks, i->new Semaphore(1));
        turn = 1;
    }


    public synchronized void getforks(int id){
        while (turn == 0){
            try{
                wait();
            }catch (InterruptedException ie){
                return;
            }
        }
        turn = 0;
        // grab forks
        int lf, rf;
        lf = id - 2;
        rf = id % numPhilos;
        //
        if(lf<0) lf = 0;
        // from id=1 to n-1, philosopher pick left then right
        if(id==numPhilos){
            // let largest ID philosopher pick right then left
            // switch left right
            lf=lf+rf;
            rf=lf-rf;
            lf=lf-rf;
        }
        try{
            forks[lf].acquire();
            forks[rf].acquire();
        }catch (InterruptedException e){
            turn = 1;
            notify();
            try{
                wait();
            } catch (InterruptedException ei){
                return;
            }
            turn = 0;
            return;
        }
        notify();
    }
}
