package com.vk.id194177937.myfinance.core.wrapper;

import com.vk.id194177937.myfinance.core.dao.interfaces.DepositoryDAO;
import com.vk.id194177937.myfinance.core.exceptions.CurrencyException;
import com.vk.id194177937.myfinance.core.interfaces.Depository;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

/**
 * Created by Inontran on 08.07.16.
 */
// синхронизирует все действия между объектами коллекции и базой данных
// паттерн Декоратор или Обертка(измененный)
public class DepositorySynchronizer implements DepositoryDAO {
    private DepositoryDAO depositoryDAO;
    private List<Depository> depositoryList;

    public DepositorySynchronizer(DepositoryDAO depositoryDAO) {
        this.depositoryDAO = depositoryDAO;
        init();
    }

    private void init() {
        depositoryList = depositoryDAO.getAll();
    }


    @Override
    public boolean addCurrency(Depository depository, Currency currency) throws CurrencyException {
        if (depositoryDAO.addCurrency(depository, currency)){
            depository.addCurrency(currency);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCurrency(Depository depository, Currency currency) throws CurrencyException {
        if (depositoryDAO.deleteCurrency(depository, currency)){
            depository.deleteCurrency(currency);
            return true;
        }
        return false;
    }

    // TODO при обновлении происходит наоборот - сначала обновляется объект в коллекции, потом уже в БД - продумать, как сделать, чтобы можно было откатить изменения в случае ошибки при запросе к БД
    @Override
    public boolean updateAmount(Depository depository, Currency currency, BigDecimal bigDecimal) {
        if (depositoryDAO.updateAmount(depository, currency, bigDecimal)){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Depository depository) {
        if (depositoryDAO.update(depository)){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Depository depository) {
        // TODO добавить нужные Exceptions
        if (depositoryDAO.delete(depository)){
            depositoryList.remove(depository);
            return true;
        }
        return false;
    }


    @Override
    public List<Depository> getAll() {
        if (depositoryList==null){
            depositoryList = depositoryDAO.getAll();
        }
        return depositoryList;
    }

    @Override
    public Depository get(long id) {
        return depositoryDAO.get(id);
    }
}
