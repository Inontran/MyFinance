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
    public static Connection getConnection(){
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            String url = "jdbc:sqlite:/home/ubuntuvod/SQLite databases/money";
            if (con == null) con = DriverManager.getConnection(url);
            return con;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            Logger.getLogger(SQLiteConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
