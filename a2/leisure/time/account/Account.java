package leisure.time.account;

import java.time.LocalDate;
import java.util.Objects;

public class Account {
    private String username;
    private LocalDate birthdayDate;

    public Account(String username, LocalDate birthdayDate) {
        this.username = username;
        this.birthdayDate = birthdayDate;
    }

    public String getUsername() {
        return this.username;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Account account = (Account) o;
        boolean sameUsernames = username.equals(account.getUsername());
        boolean sameBirthDates = birthdayDate.isEqual(account.getBirthdayDate());
        return (sameBirthDates && sameUsernames);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

}
