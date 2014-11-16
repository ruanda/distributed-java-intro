package exercise2;

public class Main {

    public static void main(String[] args) {
      Thread[] threads = new Thread[4];
      for(int i = 0; i < threads.length; i++) {
        threads[i] = new Thread(new MyThread(), "lol");
        threads[i].start();
      }
    }
}
