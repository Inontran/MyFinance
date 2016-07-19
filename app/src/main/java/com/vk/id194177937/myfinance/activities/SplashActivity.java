package com.vk.id194177937.myfinance.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vk.id194177937.myfinance.R;
import com.vk.id194177937.myfinance.app.DbConnection;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(){
            public void run(){
                //загрузка начальных данных из БД
                DbConnection.initConnection(getApplicationContext());


                //имитация загрузки данных
                imitateLoading();

                //после загрузки переходим на главное окно
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    private void imitateLoading(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
