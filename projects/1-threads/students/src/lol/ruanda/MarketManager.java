package lol.ruanda;

import java.util.ArrayList;
import java.util.List;

public class MarketManager {

    private static MarketManager instance;

    private List<Donor> donors;
    private List<Recipient> recipients;
    private boolean open = false;

    private MarketManager() {
        donors = new ArrayList<Donor>();
        recipients = new ArrayList<Recipient>();
    }

    public static MarketManager getInstance() {
        if (instance == null) {
            synchronized (MarketManager.class) {
                if (instance == null) {
                    instance = new MarketManager();
                }
            }
        }
        return instance;
    }

    public void addDonor(Donor donor) {
        donors.add(donor);
    }

    public void addRecipient(Recipient recipient) {
        recipients.add(recipient);
    }

    private void startMarket() {
        for (Donor d : donors) {
            new Thread(d).start();
        }
        for (Recipient r : recipients) {
            new Thread(r).start();
        }
        new Thread(Chairman.getInstance()).start();
    }

    private void stopMarket() {
        for (Donor d : donors) {
            d.stop();
        }
        for (Recipient r : recipients) {
            r.stop();
        }
        Chairman.getInstance().stop();
    }

    public void openMarket() {
        if (!open) {
            synchronized (this) {
                if (!open) {
                    open = true;
                    startMarket();
                }
            }
        }
    }

    public void closeMarket() {
        if (open) {
            synchronized (this) {
                if (open) {
                    open = false;
                    stopMarket();
                }
            }
        }
    }
}
