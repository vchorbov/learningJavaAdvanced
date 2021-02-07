package online.bank.revolut.card;

import online.bank.revolut.Revolut;

import java.time.LocalDate;


public abstract class AbstractCard implements Card{
    String number;
    int pin;
    LocalDate expDate;
    boolean blocked;

    AbstractCard(String number, int pin, LocalDate expirationDate){
        this.number = number;
        this.pin = pin;
        this.expDate = expirationDate;
        this.blocked = false;


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
    public boolean checkPin(int pin){
        return pin == this.pin;
    }
    @Override
    public boolean isBlocked(){
        return this.blocked;
    }

    @Override
    public void block(){
        this.blocked = true;
    }

    public String getNumber(){
        return this.number;
    }
}
