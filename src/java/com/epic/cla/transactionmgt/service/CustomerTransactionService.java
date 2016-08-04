/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.service;

import com.epic.cla.transactionmgt.bean.CustomerTransactionInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.jpos.iso.ISODate;
import org.jpos.iso.ISOUtil;

/**
 *
 * @author chalaka_n
 */
public class CustomerTransactionService {

    public boolean addData(CustomerTransactionInputBean inputBean) throws Exception {
        Connection con = null;
        String sql;
        PreparedStatement preStat = null;
        boolean ok = false;

        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "INSERT INTO CLA_ICBS_CHANNEL_REQUEST(REQUEST_AMOUNT,RECIPIENT_MOBILE_NO,SENDER_MOBILE_NO,SENDER_ACCOUNT,CHANNEL_TYPE,STATUS,TRACE_NO) "
                    + " VALUES(?,?,?,?,?,?,?)";

            preStat = con.prepareStatement(sql);

            preStat.setString(1, inputBean.getAmount());
            preStat.setString(2, inputBean.getMobileNo());
            preStat.setString(3, inputBean.getMobileNo());
            preStat.setString(4, inputBean.getAccountNo());
            preStat.setInt(5, 7);
            preStat.setString(6, "1");
            preStat.setString(7, ISODate.getTime(new java.util.Date()));

            int n = preStat.executeUpdate();
            if (n >= 0) {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (preStat != null) {
                preStat.close();
            }
            if (con != null) {
                DBConnection.dbConnectionClose(con);
            }
        }
        return ok;
    }

    public boolean addgetAccountNumber(CustomerTransactionInputBean inputBean) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        boolean isAccount=false;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = " SELECT ACCOUNT_NUMBER FROM CLA_CUSTOMER where MOBILE_NO=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, inputBean.getMobileNo());
            result = ps.executeQuery();

            if (result.next()) {
                inputBean.setAccountNo(result.getString("ACCOUNT_NUMBER"));
                isAccount=true;
            }
        } catch (Exception ex) {
            isAccount=false;
            throw ex;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (result != null) {
                result.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return isAccount;
    }

}
