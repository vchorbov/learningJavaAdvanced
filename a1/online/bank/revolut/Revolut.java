package online.bank.revolut;

import online.bank.revolut.account.Account;
import online.bank.revolut.card.AbstractCard;
import online.bank.revolut.card.Card;

import java.util.Arrays;
import java.util.List;

public class Revolut implements RevolutAPI {
    private static final double EUR_TO_BGN_EXCHANGE_RATE = 1.95583;
    private static final String BLOCKED_TOP_LEVEL_DOMAIN = "biz";
    static Account[] accounts;
    static Card[] cards;
    private static double totalBGN;


    public Revolut(Account[] accounts, Card[] cards) {
        Revolut.accounts = accounts;
        Revolut.cards = cards;
        totalBGN = 0;

    }

    @Override
    public boolean pay(Card card, int pin, double amount, String currency) {
        AbstractCard card1 = (AbstractCard) card;
        if (!card1.getType().equals("PHYSICAL")) {
            return false;
        }
        return validateOperation(card1, currency, pin, amount);

    }

    @Override
    public boolean payOnline(Card card, int pin, double amount, String currency, String shopURL) {
        AbstractCard card1 = (AbstractCard) card;
        if (shopURL.contains(BLOCKED_TOP_LEVEL_DOMAIN)) {
            return false;
        }
        return validateOperation(card1, currency, pin, amount);
    }


    @Override
    public boolean addMoney(Account account, double amount) {
        if (accountIsInAccounts(account)) {
            account.updateAmount(account.getAmount() + amount);
            return true;
        }
        return false;
    }


    @Override
    public boolean transferMoney(Account from, Account to, double amount) {
        double newAmount = 0;
        if (accountIsInAccounts(from) && accountIsInAccounts(to)) {
            if (!from.getIBAN().equals(to.getIBAN())) {
                if (from.getAmount() >= amount) {
                    if (!(from.getCurrency().equals(to.getCurrency()))
                            && from.getCurrency().equals("BGN")) {
                        newAmount = amount / EUR_TO_BGN_EXCHANGE_RATE;
                    } else if (from.getCurrency().equals("EUR")) {
                        newAmount = amount * EUR_TO_BGN_EXCHANGE_RATE;
                    } else {
                        newAmount = amount;
                    }
                    //decreases the balance of the transferring account with the send amount
                    from.updateAmount(from.getAmount() - amount);
                    // increases the other account with the same amount
                    to.updateAmount(to.getAmount() + newAmount);

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public double getTotalAmount() {
        return Arrays.stream(accounts)
                .filter(x -> x.getCurrency().equals("BGN"))
                .map(x -> x.getAmount()).reduce(totalBGN, (x, y) -> x + y);
    }

    private boolean cardIsInCards(Card card) {
        return List.of(cards).contains(card);
    }

    private boolean accountIsInAccounts(Account account) {
        return List.of(accounts).contains(account);
    }

    private boolean validateCard(Card card) {
        return (cardIsInCards(card)
                && !card.isBlocked());
    }

    private boolean validateOperation(AbstractCard card1, String currency, int pin, double amount) {
        if (validateCard(card1)) {
            if (card1.checkPin(pin)) {
                String cardNumber = card1.getNumber();
                for (Account a : accounts) {
                    if (a.getIBAN().equals(cardNumber)) {
                        if (a.getCurrency().equals(currency)) {
                            if (a.getAmount() >= amount) {
                                a.updateAmount(a.getAmount() - amount);
                                return true;

                            }
                        }
                    }

                }


            }
        }
        return false;
    }
}


