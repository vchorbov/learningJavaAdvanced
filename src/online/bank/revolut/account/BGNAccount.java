package online.bank.revolut.account;

public class BGNAccount extends Account{
    private double amount;
    private String IBAN;
    private static final String CURRENCY = "BGN";


    public BGNAccount(String IBAN, double amount){
        super(IBAN, amount);
    }


    @Override
    public String getCurrency() {
        return CURRENCY;
    }
}
