package exercise2;

import java.util.concurrent.locks.ReentrantLock;

import common.Counter;

public class LockingCounter implements Counter {

    private long c = 0;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void increment() {
      lock.lock();
      try {
        c++;
      } finally {
        lock.unlock();
      }
    }

    @Override
    public long getValue() {
        return c;
    }
}
