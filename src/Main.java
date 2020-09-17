//CMPT435 2020 Fall term
//Name: Yue Weng
//Student ID: 1121 9127
//NSID: yuw857


public class Main {
    public static void main(String[] args) {

        DiningRoom bigRoom = new DiningRoom();
        for(int i=1;i<6;i++){
            new Thread(new Philosopher(i,bigRoom, 10)).start();
        }
    }
}
