package online.bank.revolut.card;

import java.time.LocalDate;

public class VirtualPermanentCard extends AbstractCard {
    String number;
    int pin;
    LocalDate expDate;
    boolean blocked;


    public VirtualPermanentCard( String number, int pin, LocalDate expirationDate){
        super(number, pin, expirationDate);
    }

    @Override
    public String getType(){
        return "VIRTUALPERMANENT";
    }


}
