package com.vk.id194177937.myfinance.core.impls.operations;

import com.vk.id194177937.myfinance.core.abstracts.AbstractOperation;
import com.vk.id194177937.myfinance.core.interfaces.Depository;
import com.vk.id194177937.myfinance.core.interfaces.Source;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

/**
 * Created by Inontran on 06.07.16.
 */
// TODO для всех классов создать конструкторы без поля id, т.к. оно будет autoincrement
public class OutcomeOperation extends AbstractOperation {

    private Depository fromDepository;
    private Source toSource;
    private BigDecimal amount;
    private Currency currency;

    public OutcomeOperation(long id, Calendar dateTime, String addInfo, Depository fromDepository, Source toSource, BigDecimal amount, Currency currency) {
        super(id, dateTime, addInfo);
        this.fromDepository = fromDepository;
        this.toSource = toSource;
        this.amount = amount;
        this.currency = currency;
    }

    public OutcomeOperation(long id, Depository fromDepository, Source toSource, BigDecimal amount, Currency currency) {
        super(id);
        this.fromDepository = fromDepository;
        this.toSource = toSource;
        this.amount = amount;
        this.currency = currency;
    }

    public OutcomeOperation(Depository fromDepository, Source toSource, BigDecimal amount, Currency currency) {
        this.fromDepository = fromDepository;
        this.toSource = toSource;
        this.amount = amount;
        this.currency = currency;
    }


    public Depository getFromDepository() {
        return fromDepository;
    }

    public void setFromDepository(Depository fromDepository) {
        this.fromDepository = fromDepository;
    }

    public Source getToSource() {
        return toSource;
    }

    public void setToSource(Source toSource) {
        this.toSource = toSource;
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
