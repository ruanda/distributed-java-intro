package exercise2;

public class MyThread implements Runnable {
  public void run() {
    Thread currentThread = Thread.currentThread();
    long threadId = currentThread.getId();
    String threadName = currentThread.getName();
    System.out.format("ID: %d\nName: %s\n", threadId, threadName);
  }
}
