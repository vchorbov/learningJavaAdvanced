package online.bank.revolut.account;

public abstract class Account {
    private double amount;
    private String IBAN;

    public Account(String IBAN) {
        this(IBAN, 0);
    }

    public Account(String IBAN, double amount) {
        this.IBAN = IBAN;
        this.amount = amount;
    }

    public abstract String getCurrency();

    public double getAmount() {
        return amount;
    }

    public void updateAmount(double updated){
        this.amount = updated;
    }
    public String getIBAN(){
        return this.IBAN;
    }

    // complete the implementation

}
