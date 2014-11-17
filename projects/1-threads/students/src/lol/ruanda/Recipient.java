package lol.ruanda;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Recipient implements Runnable {
    
    private String name;
    private List<Item> items;

    private volatile boolean run;
    private Random random = new Random();

    private Item newItem;
    private boolean waiting;

    public Recipient(String name) {
        this.name = name;
        this.items = new ArrayList<Item>();
        this.run = true;
    }

    public String getName() {
        return name;
    }

    public void stop() {
        run = false;
    }

    public void auctionEnd(Item item) {
        synchronized (this) {
            waiting = false;
            newItem = item;
            this.notify();
        }
    }

    private boolean registerForAuction() {
        waiting = Chairman.getInstance().registerRecipient(this);
        return waiting;
    }

    private boolean waitForAuction() {
        while (run && waiting) {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                run = false;
                return false;
            }
        }
        return (newItem != null);
    }

    private void runRecipient()
      throws InterruptedException {
        int t;
        while (run) {
            t = random.nextInt(6);
            Thread.sleep(t * 1000);
            synchronized (this) {
                newItem = null;
                if (registerForAuction() && waitForAuction()) {
                    System.out.println(getName() + " won " + newItem.getName() + ".");
                    items.add(newItem);
                    t = random.nextInt(11) + 5;
                    Thread.sleep(t * 1000);
                }
            }
        }
    }

    public void run() {
        try {
            runRecipient();
        } catch (InterruptedException ex) { /* safe to ignore */ }
        String wonItems = "";
        for (Item i : items) {
            wonItems += " " + i.getName();
        }
        System.out.println(getName() + " says good bye leaving with items" + wonItems + ".");
    }
}
