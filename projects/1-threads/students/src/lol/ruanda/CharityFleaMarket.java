package lol.ruanda;

public class CharityFleaMarket {

    public static void main(String[] args) {

        MarketManager marketManager = MarketManager.getInstance();

        for (int i = 0; i < 2; i++) {
            Donor d = new Donor("donor-" + i);
            marketManager.addDonor(d);
        }

        for (int i = 0; i < 1; i++) {
            Recipient r = new Recipient("recipient-" + i);
            marketManager.addRecipient(r);
        }

        marketManager.openMarket();

    }
}
