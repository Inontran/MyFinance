package com.vk.id194177937.myfinance.core.impls.operations;

import com.vk.id194177937.myfinance.core.abstracts.AbstractOperation;
import com.vk.id194177937.myfinance.core.enums.OperationType;
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
    private BigDecimal fromAmount;// сумма перевода
    private Currency fromCurrency;// в какой валюте получили деньги

    public IncomeOperation() {
        super(OperationType.INCOME);
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

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal amount) {
        this.fromAmount = amount;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency currency) {
        this.fromCurrency = currency;
    }
}
