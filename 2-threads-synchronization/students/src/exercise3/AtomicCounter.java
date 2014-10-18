package exercise3;

import java.util.concurrent.atomic.AtomicLong;

import common.Counter;

public class AtomicCounter implements Counter {

    private AtomicLong c = new AtomicLong();

    @Override
    public void increment() {
      c.incrementAndGet();
    }

    @Override
    public long getValue() {
        return c.longValue();
    }
}
