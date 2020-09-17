public class Main {
    public static void main(String[] args) {

        DiningRoom bigRoom = new DiningRoom();
        for(int i=1;i<6;i++){
            new Thread(new Philosopher(i,bigRoom, 10)).start();
        }
    }
}
