package exercise3;

public class MyRunnable implements Runnable {
  public void run() {
    Thread currentThread = Thread.currentThread();
    long threadId = currentThread.getId();
    System.out.format("FINISHED id:%d\n", threadId);
  }
}
