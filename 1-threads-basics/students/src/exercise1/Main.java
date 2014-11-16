package exercise1;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Thread currentThread = Thread.currentThread();
        long threadId = currentThread.getId();
        String threadName = currentThread.getName();
        System.out.format("ID: %d\nName: %s\n", threadId, threadName);
    }
}
