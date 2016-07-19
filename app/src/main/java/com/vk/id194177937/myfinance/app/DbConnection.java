package com.vk.id194177937.myfinance.app;

import android.content.Context;
import android.util.Log;

import com.vk.id194177937.myfinance.core.database.Initializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Inontran on 19.07.16.
 */
public class DbConnection {

    private static final String TAG = AppContext.class.getName();
    private static final String DRIVER_CLASS = "org.sqldroid.SQLDroidDriver";
    private static final String DB_NAME = "money";
    private static String dbFolder;
    private static String dbPath;

    public static void initConnection(Context context){
        checkDbExist(context);
        Initializer.load(DRIVER_CLASS, "jdbc:sqldroid:" + dbFolder + DB_NAME);
    }

    //если нет файла БД, то скопировать его из папки assets
    private static void checkDbExist(Context context) {
        //определить папку с данными приложения
//        dbFolder = context.getApplicationContext().dataDir + "/" + "databases/";//этот способ не работает
//        dbFolder = context.getApplicationContext().getDatabasePath(DB_NAME) + "/" + "databases/";//этот возвращает /data/user/0/com.vk.id194177937.myfinance/databases/money
        dbFolder = "/data/data/" + context.getPackageName() + "/databases/";
        dbPath = dbFolder + DB_NAME;

        if (!new File(dbPath).exists()){
            copyDataBase(context);
        }
    }

    private static void copyDataBase(Context context){
        //создаем папку databases
        File databaseFolder = new File(dbFolder);
        databaseFolder.mkdir();

        try (InputStream sourceFile = context.getAssets().open(DB_NAME);
             OutputStream destinationFolder = new FileOutputStream(dbPath)){

            //копируем по байтам весь файл
            byte[] byffer = new byte[1024];
            int length;
            while ( (length = sourceFile.read(byffer)) > 0 ){
                destinationFolder.write(byffer, 0, length);
            }

        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }
}
