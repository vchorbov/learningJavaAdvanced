package online.bank.revolut.card;

import java.time.LocalDate;

public class VirtualPermanentCard extends AbstractCard {

    private static final String TYPE = "VIRTUALPERMANENT";

    public VirtualPermanentCard( String number, int pin, LocalDate expirationDate){
        super(number, pin, expirationDate);
    }

    @Override
    public String getType(){
        return TYPE;
    }


}
