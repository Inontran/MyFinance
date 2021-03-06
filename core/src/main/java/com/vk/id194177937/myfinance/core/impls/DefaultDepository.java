package com.vk.id194177937.myfinance.core.impls;

import com.vk.id194177937.myfinance.core.abstracts.AbstractTreeNode;
import com.vk.id194177937.myfinance.core.exceptions.CurrencyException;
import com.vk.id194177937.myfinance.core.exceptions.AmountException;
import com.vk.id194177937.myfinance.core.interfaces.Depository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Inontran on 03.07.16.
 */
//TODO изменить, по возможности, возврат результата методов с null на что-то более безопасное

//TODO реализовать грамотную работу с потоками (Thread.sleep)
public class DefaultDepository extends AbstractTreeNode implements Depository{
    // сразу инициализируем пустые коллекции, потому что хоть одна валюта будет
    private Map<Currency, BigDecimal> currencyAmounts = new HashMap<>();
    private List<Currency> currencyList = new ArrayList<>();

    public DefaultDepository() {
    }

    public DefaultDepository(String name) {
        super(name);
    }

    public DefaultDepository(String name, long id) {
        super(name, id);
    }

    public DefaultDepository(List<Currency> currencyList, Map<Currency, BigDecimal> currencyAmounts, String name) {
        super(name);
        this.currencyList = currencyList;
        this.currencyAmounts = currencyAmounts;
    }

    public DefaultDepository(Map<Currency, BigDecimal> currencyAmounts) {
        this.currencyAmounts = currencyAmounts;
    }

    public DefaultDepository(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }



    public void setAvailableCurrencies(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    @Override
    public Map<Currency, BigDecimal> getCurrencyAmounts() {
        return currencyAmounts;
    }

    public void setCurrencyAmounts(Map<Currency, BigDecimal> currencyAmounts) {
        this.currencyAmounts = currencyAmounts;
    }


    @Override
    public BigDecimal getAmount(Currency currency) throws CurrencyException {
//        findCurrencyIntoList(currency);
        return currencyAmounts.get(currency);
    }

    @Override
    public void updateAmount(BigDecimal amount, Currency currency) throws CurrencyException, AmountException {
        findCurrencyIntoList(currency);
        checkAmount(amount);// не даем балансу уйти в минус
        currencyAmounts.put(currency, amount);
    }

    // сумма не должна быть меньше нуля (в реальности такое невозможно, мы не можем потратить больше того, что есть)
    private void checkAmount(BigDecimal amount) throws AmountException {
        //временно уберем
//        if (amount.compareTo(BigDecimal.ZERO)<0){
//            throw new AmountException("Amount can't be < 0");
//        }

    }




    @Override
    public void addCurrency(Currency currency, BigDecimal BigDecimal) throws CurrencyException {
        if (currencyAmounts.containsKey(currency)){
            throw new CurrencyException("Currency already exist");// пока просто сообщение на англ, без локализации
        }
        currencyList.add(currency);
        currencyAmounts.put(currency, BigDecimal);
    }

    @Override
    public void deleteCurrency(Currency currency) throws CurrencyException {
        findCurrencyIntoList(currency);

        // не даем удалять валюту, если в хранилище есть деньги по этой валюте
//        if (!currencyAmounts.get(currency).equals(BigDecimal.ZERO)){
//            throw new CurrencyException("Can't delete currency with amount");
//        }

        currencyAmounts.remove(currency);
        currencyList.remove(currency);
    }

    @Override
    public List<Currency> getAvailableCurrencies() {
        return currencyList;
    }

    @Override
    public BigDecimal getApproxAmount(Currency currency) {
        // TODO реализовать расчет остатка с приведением в одну валюту
        // реализуем позже
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Currency getCurrency(String code) throws CurrencyException {
        // количество валют для каждого хранилища будет небольшим - поэтому можно провоить поиск через цикл
        // можно использовать библиотеку Apache Commons Collections
        for (Currency currency : currencyList) {
            if (currency.getCurrencyCode().equals(code)){
                return currency;
            }
        }
        throw new CurrencyException("Currency (code = "+code+" ) not exist in depository");
    }

    private void findCurrencyIntoList(Currency currency) throws CurrencyException {
        if (currencyAmounts.containsKey(currency)){
            throw new CurrencyException("Currency "+currency+" not exist");
        }
    }
}
