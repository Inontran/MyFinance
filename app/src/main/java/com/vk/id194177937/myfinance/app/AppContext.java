package com.vk.id194177937.myfinance.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Inontran on 18.07.16.
 */
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbConnection.initConnection(this);
    }
}
