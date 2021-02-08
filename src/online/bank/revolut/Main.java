package online.bank.revolut;

import online.bank.revolut.account.Account;
import online.bank.revolut.account.BGNAccount;
import online.bank.revolut.account.EURAccount;
import online.bank.revolut.card.*;

import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // first create new account and card arrays
        Account a1 = new BGNAccount("BG7000", 2000);
        Account a2 = new EURAccount("EUR2323", 900);
        Account a3 = new BGNAccount("BG2020", 802.22);
        Account a4 = new EURAccount("EUR2021", 2021.2021);

        // fill the accounts array with the initial accounts
        Account[] accounts = new Account[]{a1,a2,a3,a4};

        AbstractCard c1 = new PhysicalCard("BG7000", 7000,  LocalDate.of(1970, 1, 1));
        AbstractCard c2 = new VirtualOneTimeCard("EUR2323", 2323, LocalDate.of(2020, 2,22));
        AbstractCard c3 = new VirtualPermanentCard("BG2020", 2020, LocalDate.of(2222, 3,12));
        AbstractCard c4 = new VirtualPermanentCard("EUR2021", 2021, LocalDate.of(2222, 3,12));

        // fill the card array with the initial cards
        Card[] cards = new Card[]{c1,c2,c3,c4};

        // instantiate revolute
        Revolut revolut = new Revolut(accounts, cards);

        // tests of the method pay
       boolean flag1 = revolut.pay(c1, 70002, 100,"BGN"); // false -> invalid pin
        System.out.println(flag1);
        boolean flag2 =revolut.pay(c1, 70002, 100,"BGN"); // false -> invalid pin
        System.out.println(flag2);
        boolean flag3 =revolut.pay(c1, 70002, 100,"BGN"); // false -> invalid pin
        System.out.println(flag3);
        boolean flag4 =revolut.pay(c1, 70002, 100,"BGN"); // false -> blocked
        System.out.println(flag4);
        boolean isBlocked = c1.isBlocked(); //true
        System.out.println(isBlocked);
        System.out.println(a1.getAmount());
        boolean flag5 =revolut.pay(c1, 7000, 2000,"BGN"); // true -> amount = 0
        System.out.println(flag5);
        System.out.println(a1.getAmount());
        boolean flag6 =revolut.pay(c2, 2323, 23,"EUR"); // false -> not a physical card
        System.out.println(flag6);

         boolean flag7 = revolut.transferMoney(a3,a2,800.20); // true
         double newA1Amount = a3.getAmount(); //2800.2
         double newA3Amount = a2.getAmount(); // 2.02
        System.out.println(flag7);
        System.out.println(newA1Amount);
        System.out.println(newA3Amount);






    }
}
