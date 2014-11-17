package lol.ruanda;

import java.util.Random;

public class Donor implements Runnable {

    private String name;
    private int counter;

    private volatile boolean run;
    private Random random = new Random();

    public Donor(String name) {
        this.name = name;
        this.run = true;
        this.counter = 0;
    }

    public String getName() {
        return name;
    }

    public void stop() {
        run = false;
    }

    private boolean registerForAuction() {
        Item item = new Item(getName() + "-" + counter);
        if (Chairman.getInstance().registerItem(item)) {
            counter++;
            return true;
        } else {
            return false;
        }
    }

    private void runDonor()
      throws InterruptedException {
        int t;
        while (run) {
            t = random.nextInt(26) + 5;
            Thread.sleep(t * 1000);
            while (run && !registerForAuction()) {
                t = random.nextInt(6);
                Thread.sleep(t * 1000);
            }
        }
    }

    public void run() {
        try {
            runDonor();
        } catch (InterruptedException ex) { /* safe to ignore */ }
        System.out.println(getName() + " says good bye.");
    }
}
