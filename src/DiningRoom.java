import java.util.Arrays;
import java.util.concurrent.Semaphore;


public class DiningRoom
{
    Semaphore[] forks;
    int numPhilos;
    int turn;

    DiningRoom()
    {
        numPhilos = 5;
        forks = new Semaphore[numPhilos];
        Arrays.setAll(forks, i->new Semaphore(1));
        turn = 1;
    }

    private synchronized int[] enter(int id)
    {
        while (turn == 0){
            try{
                wait();
            }catch (InterruptedException ignored){
            }
        }
        turn = 0;
        // grab forks
        int lf, rf;
        lf = id - 2;
        rf = id % numPhilos;
        if(lf<0) lf = 0;
        // from id=1 to n-1, philosopher pick left then right
        if(id==numPhilos){
            // let largest ID philosopher pick right then left
            // switch left right
            lf=lf+rf;
            rf=lf-rf;
            lf=lf-rf;
        }
        System.out.println("leave enter");
        return new int[]{lf,rf};
    }


    public synchronized void getforks(int id)
    {
        int[] twoforks=enter(id);
        try{
            forks[twoforks[0]].acquire();
            forks[twoforks[1]].acquire();
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

    public synchronized void relforks(int id)
    {
        int[] twoforks=enter(id);
        forks[twoforks[0]].release();
        forks[twoforks[1]].release();
        notify();
    }
}
