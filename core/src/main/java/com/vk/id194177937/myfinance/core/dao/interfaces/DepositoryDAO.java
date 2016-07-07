package com.vk.id194177937.myfinance.core.dao.interfaces;

import com.vk.id194177937.myfinance.core.interfaces.Depository;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by Inontran on 07.07.16.
 */
public interface DepositoryDAO extends CommonDAO<Depository> {
    //boolean нужен чтобы удостовериться, что операция прошла успешно
    boolean addCurrency(Depository depository, Currency currency);
    boolean deleteCurrency(Depository depository, Currency currency);
    boolean updateAmount(Depository depository, BigDecimal bigDecimal);
}
