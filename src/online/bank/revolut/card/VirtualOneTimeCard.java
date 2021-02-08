package online.bank.revolut.card;

import java.time.LocalDate;

public class VirtualOneTimeCard extends AbstractCard {

    private static final String TYPE = "VIRTUALONETIME";

    public VirtualOneTimeCard(String number, int pin, LocalDate expirationDate){
        super(number, pin, expirationDate);
    }

    @Override
    public String getType(){
        return TYPE;
    }
}
