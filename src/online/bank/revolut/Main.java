package online.bank.revolut;

import online.bank.revolut.account.Account;
import online.bank.revolut.account.BGNAccount;
import online.bank.revolut.account.EURAccount;
import online.bank.revolut.card.Card;
import online.bank.revolut.card.PhysicalCard;
import online.bank.revolut.card.VirtualOneTimeCard;
import online.bank.revolut.card.VirtualPermanentCard;

import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // first create new account and card arrays
        Account a1 = new BGNAccount("BGUNCR7000", 20);
        Account a2 = new EURAccount("EUR2323", 90);
        Account a3 = new BGNAccount("BGUNCR2020", 80);

        // fill the accounts array with the initial accounts
        Account[] accounts = new Account[]{a1,a2,a3};

        Card c1 = new PhysicalCard("BGUNCR7000", 1234,  LocalDate.of(1970, 1, 1));
        Card c2 = new VirtualOneTimeCard("EUR2323", 1232, LocalDate.of(2020, 2,22));
        Card c3 = new VirtualPermanentCard("BGUNCR2020", 1122, LocalDate.of(2222, 3,12));

        // fill the card array with the initial cards
        Card[] cards = new Card[]{c1,c2,c3};

        // instantiate revolute
        Revolut revolut = new Revolut(accounts, cards);



      //  Arrays.stream(Revolut.cards).forEach(x -> System.out.println(x));


         double amount = revolut.getTotalAmount();
         boolean contains = revolut.pay(c1, 1234, 22, "BGN");
        boolean successfulTransfer = revolut.transferMoney(a1,a3,30);
        System.out.println(contains);
        System.out.println(amount);
        System.out.println(successfulTransfer);

    }
}
