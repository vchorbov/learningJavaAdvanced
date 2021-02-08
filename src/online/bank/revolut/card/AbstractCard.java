package online.bank.revolut.card;

import online.bank.revolut.Revolut;

import java.time.LocalDate;
import java.util.Objects;


public abstract class AbstractCard implements Card{
    private static final int MAX_PIN_ATTEMPTS = 3;

    private String number;
    private int pin;
    private LocalDate expDate;
    private boolean blocked;
    private int incorrectPinAttempts;

    AbstractCard(String number, int pin, LocalDate expirationDate){
        this.number = number;
        this.pin = pin;
        this.expDate = expirationDate;
        this.blocked = false;
        this.incorrectPinAttempts = 0;


    }
    @Override
    public String getType(){
        return "AbstractCard";
    }

    @Override
    public LocalDate getExpirationDate(){
        return this.expDate;
    }

    @Override
    public boolean checkPin(int pin) {
        if (this.pin != pin) {
            if(++incorrectPinAttempts >= MAX_PIN_ATTEMPTS) {
                blocked = true;
            }
            return false;
        }

        incorrectPinAttempts = 0;
        return true;
    }

    @Override
    public boolean isBlocked(){
        return this.blocked;
    }

    @Override
    public void block(){
        this.blocked = true;
    }

    public String getNumber(){return this.number;}

    @Override
    public boolean equals(Object o){
        if (this==o) return true;

        AbstractCard abstractCard = (AbstractCard) o;

        return (abstractCard.number.equals(this.number)
        && abstractCard.expDate.equals(this.expDate)
        && abstractCard.pin == this.pin);

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.number);
    }




}
