package com.vk.id194177937.myfinance.core.database;

import com.vk.id194177937.myfinance.core.dao.impls.DepositoryDAOImpl;
import com.vk.id194177937.myfinance.core.dao.impls.OperationDAOImpl;
import com.vk.id194177937.myfinance.core.dao.impls.SourceDAOImpl;
import com.vk.id194177937.myfinance.core.wrapper.DepositorySynchronizer;
import com.vk.id194177937.myfinance.core.wrapper.OperationSynchronizer;
import com.vk.id194177937.myfinance.core.wrapper.SourceSynchronizer;

/**
 * Created by Inontran on 19.07.16.
 */
public class Initializer {

    private static OperationSynchronizer operationSync;
    private static SourceSynchronizer sourceSync;
    private static DepositorySynchronizer depositorySync;

    public static OperationSynchronizer getOperationSync() {
        return operationSync;
    }

    public static SourceSynchronizer getSourceSync() {
        return sourceSync;
    }

    public static DepositorySynchronizer getDepositorySync() {
        return depositorySync;
    }

    public static void load(String driverName, String url){
        SQLiteConnection.init(driverName, url);

        //последовательность создания объектов важна: сначала справочные значения, потом операции
        sourceSync = new SourceSynchronizer(new SourceDAOImpl());
        depositorySync = new DepositorySynchronizer(new DepositoryDAOImpl());
        operationSync = new OperationSynchronizer(new OperationDAOImpl(sourceSync.getIdentityMap(), depositorySync.getIdentityMap()), sourceSync, depositorySync);
    }
}
