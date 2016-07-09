package com.vk.id194177937.myfinance.core;

import com.vk.id194177937.myfinance.core.dao.impls.DepositoryDAOImpl;
import com.vk.id194177937.myfinance.core.dao.impls.SourceDAOImpl;
import com.vk.id194177937.myfinance.core.database.SQLiteConnection;
import com.vk.id194177937.myfinance.core.enums.OperationType;
import com.vk.id194177937.myfinance.core.exceptions.CurrencyException;
import com.vk.id194177937.myfinance.core.interfaces.Depository;
import com.vk.id194177937.myfinance.core.wrapper.DepositorySynchronizer;
import com.vk.id194177937.myfinance.core.wrapper.SourceSynchronizer;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Currency;

/**
 * Created by Inontran on 07.07.16.
 */
public class Tester {
    public static void main(String[] args) {
//        SourceSynchronizer synchronizer = new SourceSynchronizer(new SourceDAOImpl());
//        synchronizer.getAll();
//        synchronizer.getList(OperationType.getType(1));

        DepositorySynchronizer synchronizer = new DepositorySynchronizer(new DepositoryDAOImpl());
        Depository depository = synchronizer.get(8);
        System.out.println(depository.getName());
        try{
            synchronizer.deleteCurrency(depository, Currency.getInstance("USD"));
        } catch (CurrencyException e) {
            e.printStackTrace();
        }

    }
}
