package exercise1.equipment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Paints {
    private final Lock lock = new ReentrantLock();
    private final Condition paintAvailable = lock.newCondition();

    private int available = 3;

    public void takePaint() throws InterruptedException {
        lock.lock();
        try {
            while(available == 0) {
                paintAvailable.await();
            }
            if (available == 0) {
                throw new IllegalStateException("There are no more paints!");
            }
            available -= 1;
        } finally {
            lock.unlock();
        }
    }

    public void returnPaint() {
        lock.lock();
        try {
            available += 1;
            paintAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
