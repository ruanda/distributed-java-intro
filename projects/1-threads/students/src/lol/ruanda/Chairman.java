package lol.ruanda;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Chairman implements Runnable {

    private static Chairman instance;

    public static Chairman getInstance() {
        if (instance == null) {
            synchronized (Chairman.class) {
                if (instance == null) {
                    instance = new Chairman();
                }
            }
        }
        return instance;
    }

    private final BlockingQueue<Item> items;
    private final List<Recipient> recipients;

    private boolean beforeAuction;

    private volatile boolean run;
    private Random random = new Random();

    private Chairman() {
        items = new ArrayBlockingQueue<Item>(10);
        recipients = new ArrayList<Recipient>();
        run = true;
        beforeAuction = true;
    }

    public void stop() {
        run = false;
    }

    public boolean registerItem(Item item) {
        return (items.offer(item));
    }

    public boolean registerRecipient(Recipient recipient) {
        synchronized (this) {
            if (run && beforeAuction && recipients.size() < 10) {
                System.out.println("Registering " + recipient.getName() + ".");
                recipients.add(recipient);
                return true;
            } else {
                return false;
            }
        }
    }

    public void runChairman()
      throws InterruptedException {
        while (run) {
            Thread.sleep(5000);
            Item item = items.peek();
            if (item == null) {
                Thread.sleep(5000);
                item = items.peek();
                if (item == null) {
                    System.out.println("No auctions within 5 seconds. Closing the market.");
                    MarketManager.getInstance().closeMarket();
                }
            }
            if (item != null) {
                synchronized (this) {
                    beforeAuction = false;
                }
                if (recipients.isEmpty()) {
                    System.out.println("There is no winner for " + item.getName() + ".");
                } else {
                    int pos = random.nextInt(recipients.size());
                    Recipient winner = recipients.remove(pos);
                    winner.auctionEnd(items.poll());
                    for (Recipient r : recipients) {
                        r.auctionEnd(null);
                    }
                    recipients.clear();
                }
                synchronized (this) {
                    beforeAuction = true;
                }
            }
        }
        for (Recipient r : recipients) {
            r.auctionEnd(null);
        }
        recipients.clear();
    }

    public void run() {
        try {
            runChairman();
        } catch (InterruptedException ex) { /* safe to ignore */ }
        System.out.println("Chairman says good bye.");
    }
}
