package online.bank.revolut.account;

public class EURAccount extends Account{
    private double amount;
    private String IBAN;

    public EURAccount(String IBAN){
        super(IBAN);
    }
    public EURAccount(String IBAN, double amount){
        super(IBAN, amount);
    }

    @Override
    public String getCurrency() {
        return "EUR";
    }
}
