package com.vk.id194177937.myfinance.core.impls.operations;

import com.vk.id194177937.myfinance.core.abstracts.AbstractOperation;
import com.vk.id194177937.myfinance.core.interfaces.Depository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

/**
 * Created by Inontran on 06.07.16.
 */
public class ConvertOperation extends AbstractOperation {

    private Depository fromDepository;
    private Depository toDepository;
    private Currency fromCurrency;
    private Currency toCurrency;
    private BigDecimal fromAmount;
    private BigDecimal toAmount;

    public ConvertOperation(long id, Calendar dateTime, String addInfo, Depository fromDepository, Depository toDepository, Currency fromCurrency, Currency toCurrency, BigDecimal fromAmount, BigDecimal toAmount) {
        super(id, dateTime, addInfo);
        this.fromDepository = fromDepository;
        this.toDepository = toDepository;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
    }

    public ConvertOperation(long id, Depository fromDepository, Depository toDepository, Currency fromCurrency, Currency toCurrency, BigDecimal fromAmount, BigDecimal toAmount) {
        super(id);
        this.fromDepository = fromDepository;
        this.toDepository = toDepository;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
    }

    public ConvertOperation(Depository fromDepository, Depository toDepository, Currency fromCurrency, Currency toCurrency, BigDecimal fromAmount, BigDecimal toAmount) {
        this.fromDepository = fromDepository;
        this.toDepository = toDepository;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
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

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(BigDecimal fromAmount) {
        this.fromAmount = fromAmount;
    }

    public BigDecimal getToAmount() {
        return toAmount;
    }

    public void setToAmount(BigDecimal toAmount) {
        this.toAmount = toAmount;
    }
}
