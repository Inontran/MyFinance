package com.vk.id194177937.myfinance.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Inontran on 07.07.16.
 */
public class SQLiteConnection {

    private static Connection con;
    private static String urlConnection;
    private static String driverClassName;

    public static void init(String driverName, String url){
        urlConnection = url;
        driverClassName = driverName;
        getConnection();
    }

    public static Connection getConnection(){
        try {
            Class.forName(driverClassName).newInstance();
            if (con == null) {
                con = DriverManager.getConnection(urlConnection);
                con.createStatement().execute("PRAGMA foreign_keys = ON");
                con.createStatement().execute("PRAGMA encoding = \"UTF-8\"");
            }
            return con;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
