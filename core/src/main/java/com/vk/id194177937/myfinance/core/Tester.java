package com.vk.id194177937.myfinance.core;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.vk.id194177937.myfinance.core.dao.impls.DepositoryDAOImpl;
import com.vk.id194177937.myfinance.core.dao.impls.OperationDAOImpl;
import com.vk.id194177937.myfinance.core.dao.impls.SourceDAOImpl;
import com.vk.id194177937.myfinance.core.database.SQLiteConnection;
import com.vk.id194177937.myfinance.core.enums.OperationType;
import com.vk.id194177937.myfinance.core.exceptions.CurrencyException;
import com.vk.id194177937.myfinance.core.impls.DefaultDepository;
import com.vk.id194177937.myfinance.core.impls.DefaultSource;
import com.vk.id194177937.myfinance.core.impls.operations.ConvertOperation;
import com.vk.id194177937.myfinance.core.impls.operations.IncomeOperation;
import com.vk.id194177937.myfinance.core.impls.operations.TransferOperation;
import com.vk.id194177937.myfinance.core.interfaces.Depository;
import com.vk.id194177937.myfinance.core.interfaces.Source;
import com.vk.id194177937.myfinance.core.wrapper.DepositorySynchronizer;
import com.vk.id194177937.myfinance.core.wrapper.OperationSynchronizer;
import com.vk.id194177937.myfinance.core.wrapper.SourceSynchronizer;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Currency;

/**
 * Created by Inontran on 07.07.16.
 */
public class Tester {
    public static void main(String[] args) {
        SourceSynchronizer sourceSync = new SourceSynchronizer(new SourceDAOImpl());
        DepositorySynchronizer depositorySync = new DepositorySynchronizer(new DepositoryDAOImpl());
        OperationSynchronizer operationSync =
                new OperationSynchronizer(new OperationDAOImpl(sourceSync.getIdentityMap(), depositorySync.getIdentityMap()), sourceSync, depositorySync);


//        DefaultDepository defaultDepository = new DefaultDepository("def depo");
//        Depository depository = depositorySync.get(13);
//        try {
//            defaultDepository.addCurrency(Currency.getInstance("RUB"), new BigDecimal(145));
//            defaultDepository.setParent(depository);
//            depositorySync.add(defaultDepository);
//            System.out.println("sdf");
////            depositorySync.delete(depositorySync.get(19));
//        } catch (CurrencyException e) {
//            e.printStackTrace();
//        }

        try {
//            Depository depository = depositorySync.get(12);
//            System.out.println(depository.getAmount(Currency.getInstance("USD")));
//            Source source = sourceSync.get(11);
//
//            IncomeOperation incomeOperation = new IncomeOperation();
//            incomeOperation.setFromCurrency(Currency.getInstance("USD"));
//            incomeOperation.setFromAmount(new BigDecimal(200));
//            incomeOperation.setToDepository(depository);
//            incomeOperation.setFromSource(source);
//            incomeOperation.setDateTime(Calendar.getInstance());
//            incomeOperation.setDescription("test income operation");
//            operationSync.add(incomeOperation);
//            System.out.println(incomeOperation);

//            TransferOperation transferOperation = new TransferOperation();
//            transferOperation.setFromCurrency(depositorySync.get(12).getCurrency("RUB"));
//            transferOperation.setFromAmount(new BigDecimal(1));
//            transferOperation.setFromDepository(depositorySync.get(12));
//            transferOperation.setToDepository(depositorySync.get(1));
//            transferOperation.setDateTime(Calendar.getInstance());
//            transferOperation.setDescription("transfer test");
//            operationSync.add(transferOperation);

//            ConvertOperation operation = new ConvertOperation();
//            Depository depository1 = depositorySync.get(1);
//            Depository depository2 = depositorySync.get(12);
//
//            System.out.println(depository1.getAmount(Currency.getInstance("RUB")));
//
//            operation.setFromCurrency(Currency.getInstance("USD"));
//            operation.setFromAmount(new BigDecimal(100));
//            operation.setFromDepository(depository2);
//
//            operation.setToCurrency(Currency.getInstance("RUB"));
//            operation.setToAmount(new BigDecimal(200));
//            operation.setToDepository(depository1);
//
//            operation.setDateTime(Calendar.getInstance());
//            operation.setDescription("test convert");
//
//            operationSync.add(operation);

            operationSync.delete(operationSync.get(19));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
