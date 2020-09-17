//CMPT435 2020 Fall term
//Name: Yue Weng
//Student ID: 1121 9127
//NSID: yuw857

/**
 * Main program
 */

public class Main {
    /**
     * Running program has two way:
     * First way: not giving any argument, running with default argument
     * rounds =5, time  bounds=5
     *
     * Second way: give valid round number and time bound
     * args 0: int, rounds of eating
     * args 1: positive int, random range of single eating/thinking time.*
     */
    public static void main(String[] args) {

        int setRounds = 5;
        int setTimeBounds = 5;


        if (args.length == 2){
            try{
                setRounds = Integer.parseInt(args[0]);
                if (setRounds <=0) throw new Exception();
                setTimeBounds = Integer.parseInt(args[1]);
                if (setTimeBounds <=0) throw new Exception();
            }catch (Exception e){
                System.out.println("Error: Invalid argument of round and time");
                System.out.println("Example: java Main 5 5");
                return;
            }
        } else if (args.length >0){
            System.out.println("Error: Invalid argument of round and time");
            System.out.println("Example: java Main 5 5");
            return;
        }

        DiningRoom bigRoom = new DiningRoom();
        for(int i=1;i<6;i++){
            // create 5 thread for 5 philosopher
            new Thread(new Philosopher(i,bigRoom, setRounds, setTimeBounds)).start();
        }
    }
}
