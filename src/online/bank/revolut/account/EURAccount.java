package online.bank.revolut.account;

public class EURAccount extends Account{
    private double amount;
    private String IBAN;
    private static final  String CURRENCY = "EUR";


    public EURAccount(String IBAN, double amount){
        super(IBAN, amount);
    }

    @Override
    public String getCurrency() {
        return CURRENCY;
    }
}
