package com.vk.id194177937.myfinance.core.interfaces;

import com.vk.id194177937.myfinance.core.exceptions.AmountException;
import com.vk.id194177937.myfinance.core.exceptions.CurrencyException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;

/**
 * Created by Inontran on 03.07.16.
 */
// TODO изменить тип BigDecimal на готовый класс по работе с деньгами Money
public interface Depository extends TreeNode{

    // получение баланса (остатка)
    Map<Currency, BigDecimal> getCurrencyAmounts(); // остаток по каждой доступной валюте в хранилище
    BigDecimal getAmount(Currency currency) throws CurrencyException; // остаток по определенной валюте
    BigDecimal getApproxAmount(Currency currency);// примерный остаток в переводе всех денег в одну валюту


    // изменение баланса
    void updateAmount(BigDecimal amount, Currency currency) throws CurrencyException, AmountException; // изменение баланса по определенной валюте

    // работа с валютой
    void addCurrency(Currency currency, BigDecimal bigDecimal) throws CurrencyException; // добавить новую валюту в хранилище
    void deleteCurrency(Currency currency) throws CurrencyException; // удалить валюту из хранилища
    Currency getCurrency(String code) throws CurrencyException; // получить валюту по коду
    List<Currency> getAvailableCurrencies(); // получить все доступные валюты хранилища в отдельной коллекции
}
