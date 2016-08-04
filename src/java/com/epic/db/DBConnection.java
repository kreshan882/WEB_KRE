/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.db;

import com.epic.init.InitConfigValue;
import com.epic.util.LogFileCreator;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import snaq.db.ConnectionPool;

/**
 *
 * @author tharaka
 */
public class DBConnection {

    public static ConnectionPool pool = null;

    public static synchronized Connection getConnection() throws Exception {
        Connection conn = null;

        try {
            conn = pool.getConnection(InitConfigValue.DBCONNECTIONTIMEOUT);
            conn.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return conn;
    }

    public static synchronized void dbConnectionClose(Connection conn) throws Exception {
        try {
            conn.close();
            conn = null;
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            throw ex;
        }
    }

    public static void createDbPool() throws Exception {

        Driver driver = (Driver) Class.forName(InitConfigValue.DBDRIVER).newInstance();
        DriverManager.registerDriver(driver);

        pool = new ConnectionPool("Oracal",
                InitConfigValue.MINPOOL,
                InitConfigValue.MAXPOOL,
                InitConfigValue.MAXCON,
                InitConfigValue.DBCONEXPIRTIMEOUT,
                InitConfigValue.DBURL,
                InitConfigValue.DBUSERNAME,
                InitConfigValue.DBPASSWORD);
        Connection con = getConnection();
        dbConnectionClose(con);

    }

}
