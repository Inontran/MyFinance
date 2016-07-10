package com.vk.id194177937.myfinance.core.impls.operations;

import com.vk.id194177937.myfinance.core.abstracts.AbstractOperation;
import com.vk.id194177937.myfinance.core.enums.OperationType;
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
    private BigDecimal fromAmount;// сумма перевода
    private Currency fromCurrency;// в какой валюте получили деньги

    public TransferOperation() {
        super(OperationType.TRANSFER);
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

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
}
