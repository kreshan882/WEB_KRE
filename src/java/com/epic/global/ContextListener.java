/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.global;

import com.epic.db.DBConnection;
import com.epic.db.DBProcesses;
import com.epic.init.InitConfigValue;
import com.epic.init.InitConfigValueReader;
import com.epic.login.bean.SessionUserBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author kreshan
 */
public class ContextListener implements ServletContextListener {

    SessionUserBean sub = new SessionUserBean();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            InitConfigValue.SWITCH_ENGIN_IP = "192.168.116.247";
            InitConfigValue.SERVICEPORT = 5001;
            InitConfigValue.LOGBACKUPPATH = "/opt/logbackup";
//                    InitConfigValue.SWITCH_ENGIN_IP = "127.0.0.1";
            InitConfigValue.SWITCH_ENGIN_PORT = 1111;
            InitConfigValue.LOGPATH = "/opt/epiccla/logs/web/";

            if (System.getProperty("os.name").startsWith("Windows")) {
                InitConfigValue.SCONFIGPATH = "C:\\epiccla\\conf\\";
            } else if (System.getProperty("os.name").startsWith("Linux")) {
                InitConfigValue.SCONFIGPATH = "/opt/epiccla/conf/";
            }

            InitConfigValueReader.readConfigValues();
            DBConnection.createDbPool();

            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, "Global Variable Initialized kre.");

        } catch (Exception e) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
