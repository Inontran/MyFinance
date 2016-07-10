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
public class OutcomeOperation extends AbstractOperation {

    private Depository fromDepository;
    private Source toSource;
    private BigDecimal fromAmount;
    private Currency fromCurrency;

    public OutcomeOperation() {
        super(OperationType.OUTCOME);
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
