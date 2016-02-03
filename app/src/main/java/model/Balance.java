package model;

import java.util.Date;

/**
 * Created by Florea on 11/16/2015.
 */
public class Balance {
    private double amount;
    private Date balanceDate;

    public Balance(double amount, Date balanceDate) {
        this.amount = amount;
        this.balanceDate = balanceDate;
    }

    public Balance() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
