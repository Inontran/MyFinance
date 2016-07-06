package com.vk.id194177937.myfinance.core.impls.operations;

import com.vk.id194177937.myfinance.core.abstracts.AbstractOperation;
import com.vk.id194177937.myfinance.core.interfaces.Depository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

/**
 * Created by Inontran on 06.07.16.
 */
public class TransferOperation extends AbstractOperation {

    private Depository fromDepository;
    private Depository toDepository;
    private BigDecimal amount;
    private Currency currency;

    public TransferOperation(long id, Calendar dateTime, String addInfo, Depository fromDepository, Depository toDepository, BigDecimal amount, Currency currency) {
        super(id, dateTime, addInfo);
        this.fromDepository = fromDepository;
        this.toDepository = toDepository;
        this.amount = amount;
        this.currency = currency;
    }

    public TransferOperation(long id, Depository fromDepository, Depository toDepository, BigDecimal amount, Currency currency) {
        super(id);
        this.fromDepository = fromDepository;
        this.toDepository = toDepository;
        this.amount = amount;
        this.currency = currency;
    }

    public TransferOperation(Depository fromDepository, Depository toDepository, BigDecimal amount, Currency currency) {
        this.fromDepository = fromDepository;
        this.toDepository = toDepository;
        this.amount = amount;
        this.currency = currency;
    }



    public Depository getFromDepository() {
        return fromDepository;
    }

    public void setFromDepository(Depository fromDepository) {
        this.fromDepository = fromDepository;
    }

    public Depository getToDepository() {
        return toDepository;
    }

    public void setToDepository(Depository toDepository) {
        this.toDepository = toDepository;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
