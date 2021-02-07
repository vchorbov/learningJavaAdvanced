package online.bank.revolut.card;

import java.time.LocalDate;

public class PhysicalCard extends AbstractCard {
    String number;
    int pin;
    LocalDate expDate;
    boolean blocked;


    public PhysicalCard(String number, int pin, LocalDate expirationDate) {
        super(number, pin, expirationDate);
    }

    @Override
    public String getType(){
        return  "PHYSICAL";
    }
}
