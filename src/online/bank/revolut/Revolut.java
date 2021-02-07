package online.bank.revolut;

import online.bank.revolut.account.Account;
import online.bank.revolut.card.Card;

import java.util.Arrays;
import java.util.List;

public class Revolut implements RevolutAPI{
    static Account[] accounts;
    static Card[] cards;
    private static int indexCards;
    private static int indexAccounts;
    private static double totalBGN;
    private int invalidPinCounter;


    public Revolut(Account[] accounts, Card[] cards){
        Revolut.accounts = accounts;
        Revolut.cards = cards;
        indexAccounts = 0;
        indexCards = 0;
        totalBGN = 0;
        invalidPinCounter = 0;
    }

    @Override
    public boolean pay(Card card, int pin, double amount, String currency) {
        if(List.of(cards).contains(card)){
             return true;
        }

        return false;
    }
    /**
     * Executes an online card payment
     *
     * @param card the card used for the payment. Any type of card is accepted
     * @param pin 4-digit PIN
     * @param amount the amount paid
     * @param currency the currency of the payment ("BGN" or "EUR")
     * @param shopURL the shop's domain name. ".biz" top level domains are currently banned and payments should be rejected
     * @return true, if the operation is successful and false otherwise.
     *         Payment is successful, if the card is available in Revolut, valid, unblocked,
     *         the specified PIN is correct and an account with sufficient amount in the specified currency exists.
     *         In case of three consecutive incorrect PIN payment attempts, the card should be blocked.
     **/

    @Override
    public boolean payOnline(Card card, int pin, double amount, String currency, String shopURL) {
        if(validateCard(card)){
            if(card.checkPin(pin)){
                invalidPinCounter = 0;
                //find account in the same currency with the same number
                
            }else{
                invalidPinCounter++;
                if(invalidPinCounter==3){
                    card.block();
                    invalidPinCounter = 0;
                }

            }

        }
        return false;
    }

    /**
     * Adds money to a Revolut account
     *
     * @param account the account to debit
     * @param amount the amount to add to the account, in the @account's currency
     * @return true, if the account exists in Revolut and false otherwise
     **/
    @Override
    public boolean addMoney(Account account, double amount) {
        if(accountIsInAccounts(account)){
            account.updateAmount(account.getAmount()+amount);
            return true;
        }
        return false;
    }

    /**
     * Transfers money between accounts, doing currency conversion, if needed.
     * The official fixed EUR to BGN exchange rate is 1.95583.
     *
     * @param from the account to credit
     * @param to the account to debit
     * @param amount the amount to transfer, in the @from account's currency
     * @return true if both accounts exist and are different (with different IBANs) and false otherwise
     **/
    @Override
    public boolean transferMoney(Account from, Account to, double amount) {
        if (accountIsInAccounts(from) && accountIsInAccounts(to)) {
            if (!from.getIBAN().equals(to.getIBAN())) {
                if (from.getAmount()>=amount) {
                    if (!(from.getCurrency().equals(to.getCurrency()))
                            && from.getCurrency().equals("BGN")) {
                        amount = amount / 1.95583;
                    } else {
                        amount = amount * 1.95583;
                    }
                    //decreases the balance of the transferring account with the send amount
                    from.updateAmount(from.getAmount() - amount);
                    // increases the other account with the same amount
                    to.updateAmount(to.getAmount() + amount);

                    return true;
                }
            }
        }
            return false;
    }
    /**
     * Returns the total available amount
     *
     * @return The total available amount (the sum of amounts for all accounts), in BGN
     **/

    @Override
    public double getTotalAmount() {
         return Arrays.stream(accounts)
                 .filter(x-> x.getCurrency().equals("BGN"))
                 .map(x -> x.getAmount()).reduce(totalBGN,(x,y) -> x+y);
    }

    private boolean cardIsInCards(Card card){
        if(cardIsInCards(card)){
            return true;
        }
        return false;
    }
    private boolean accountIsInAccounts(Account account){
        if(List.of(accounts).contains(account)){
            return true;
        }
        return false;
    }

    private boolean validateCard(Card card){
        if(cardIsInCards(card)
                && !card.isBlocked()){
            return true;
        }
        return false;
    }

    /*
     *methods for different logic
     *
     */
    public static void addCardInCards(Card card){
        cards[indexCards] = card;
        indexCards++;
    }

    public static void addAccountInAccounts(Account account){
        accounts[indexAccounts] = account;
        indexAccounts++;
    }

    private int getIndexCards(){
        return indexCards;
    }

    private int getIndexAccounts(){
        return indexAccounts;
    }

    private boolean checkIfValid(Card card){
         return List.of(cards).contains(card);
     }


}
