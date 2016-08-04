/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.db;

import com.epic.init.InitConfigValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author tharaka
 */
public class DBProcesses {

    private static PreparedStatement preStat = null;
    private static ResultSet ResultSet_DateTime = null;

    public static void insertHistoryRecord(String userName, String module, String section, String task, String description, String ip) throws Exception {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "INSERT INTO CLA_AUDIT_HISTORY(USER_ID,MODULE_ID,SECTION_ID,   TASK_ID,DESCRIPTION,IP)  "
                    + "VALUES (?,?,?,?,?,?)";
            preStat = con.prepareStatement(sql);

            preStat.setString(1, userName);
            preStat.setString(2, module);
            preStat.setString(3, section);
            preStat.setString(4, task);
            preStat.setString(5, description);
            preStat.setString(6, ip);
            preStat.executeUpdate();

        } catch (Exception e) {
            throw e;
        } finally {
            if (preStat != null) {
                preStat.close();
            }

            if (con != null) {
                con.close();
            }
        }

    }

}
