package exercise3;

public class Main {

    public static void main(String[] args) {

      Thread[] threads = new Thread[15];

      for(int i = 0; i < threads.length; i++) {
        threads[i] = new Thread(new MyRunnable(), "lol");
        threads[i].start();
      }

      /* naive approach
      try {
        Thread.sleep(5000);
      } catch(InterruptedException e) {}
      System.out.println("FINISHED");
      */

      /* continuous polling
WAIT_LOOP:
      while(true) {
        try {
          Thread.sleep(200);
        } catch(InterruptedException e) {}
        for(int i = 0; i < threads.length; i++) {
          if(threads[i].isAlive()) {
            continue WAIT_LOOP;
          }
        }
        break WAIT_LOOP;
      }
      System.out.println("FINISHED");
      */

      for(int i = 0; i < threads.length; i++) {
        try {
          threads[i].join();
        } catch(InterruptedException e) {}
      }
      System.out.println("FINISHED");
    }
}
