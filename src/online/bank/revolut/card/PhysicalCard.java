package online.bank.revolut.card;

import java.time.LocalDate;

public class PhysicalCard extends AbstractCard {

    private static final String TYPE = "PHYSICAL";

    public PhysicalCard(String number, int pin, LocalDate expirationDate) {
        super(number, pin, expirationDate);
    }

    @Override
    public String getType(){
        return TYPE;
    }
}
