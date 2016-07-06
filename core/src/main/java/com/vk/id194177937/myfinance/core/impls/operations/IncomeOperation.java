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
public class IncomeOperation extends AbstractOperation {

    private Source fromSource;
    private Depository toDepository;
    private BigDecimal amount;
    private Currency currency;

    public IncomeOperation(long id, Calendar dateTime, String addInfo, Source fromSource, Depository toDepository, BigDecimal amount, Currency currency) {
        super(id, dateTime, addInfo);
        this.fromSource = fromSource;
        this.toDepository = toDepository;
        this.amount = amount;
        this.currency = currency;
    }


    public IncomeOperation(long id, Source fromSource, Depository toDepository, BigDecimal amount, Currency currency) {
        super(id);
        this.fromSource = fromSource;
        this.toDepository = toDepository;
        this.amount = amount;
        this.currency = currency;
    }

    public IncomeOperation(Source fromSource, Depository toDepository, BigDecimal amount, Currency currency) {
        this.fromSource = fromSource;
        this.toDepository = toDepository;
        this.amount = amount;
        this.currency = currency;
    }



    public Source getFromSource() {
        return fromSource;
    }

    public void setFromSource(Source fromSource) {
        this.fromSource = fromSource;
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
