package com.vk.id194177937.myfinance.core;

import com.vk.id194177937.myfinance.core.database.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Inontran on 07.07.16.
 */
public class Tester {
    public static void main(String[] args) {
        try (Statement st = SQLiteConnection.getConnection().createStatement(); ResultSet rs = st.executeQuery("select * from depository") ){
            while (rs.next()){
                System.out.println(rs.getString("name"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
