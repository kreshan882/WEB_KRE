/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.service;

import com.epic.cla.transactionmgt.bean.TransactionSmsHistoryBean;
import com.epic.cla.transactionmgt.bean.TransactionSmsHistoryInputBean;
import com.epic.db.DBConnection;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nipun_t
 */
public class TransactionSmsHistoryService {

    public List<TransactionSmsHistoryBean> getTxnHistory(TransactionSmsHistoryInputBean bean, String orderBy, int rows, int from, String txanctionid) throws SQLException, Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getChannelListQuery = null;
        List<TransactionSmsHistoryBean> dataList = null;
        long totalCount = 0;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_SMS_ALERT where TXN_ID LIKE ? ";
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setString(1, "%" + txanctionid + "%");

            res = prepSt.executeQuery();
            if (res.next()) {
                totalCount = res.getLong("TOTAL");
            }

            if (prepSt != null) {
                prepSt.close();
            }
            if (res != null) {
                res.close();
            }

            getChannelListQuery = "SELECT SA.ID,SA.TXN_ID,DP.DESCRIPTION AS DELIVERY_PARTY,\n"
                    + "SA.MOBILE_NO,SA.SMS_MSG,SA.TIMESTAMP FROM CLA_SMS_ALERT SA\n"
                    + ",CLA_MT_MSG_DELIVERY_PARTY DP WHERE SA.DELIVERY_PARTY=DP.CODE AND SA.TXN_ID LIKE ?"
                    + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getChannelListQuery);
            prepSt.setString(1, "%" + txanctionid + "%");
            res = prepSt.executeQuery();

            dataList = new ArrayList<TransactionSmsHistoryBean>();

            while (res.next()) {

                TransactionSmsHistoryBean dataBean = new TransactionSmsHistoryBean();
                dataBean.setId(res.getString("ID"));
                dataBean.setTxnid(res.getString("TXN_ID"));
                dataBean.setDeliveryparty(res.getString("DELIVERY_PARTY"));
                dataBean.setSmsmsg(res.getString("SMS_MSG"));
                dataBean.setMobileno(res.getString("MOBILE_NO"));
                dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")));
                dataBean.setFullCount(totalCount);
                dataList.add(dataBean);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (prepSt != null) {
                prepSt.close();
            }
            if (res != null) {
                res.close();
            }
            if (con != null) {
                con.close();
            }

        }
        return dataList;
    }

}
