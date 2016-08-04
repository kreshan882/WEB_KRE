/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.service;

import com.epic.cla.transactionmgt.bean.TransactionHistoryBean;
import com.epic.cla.transactionmgt.bean.TransactionHistoryInputBean;
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
public class TransactionHistoryService {

    public List<TransactionHistoryBean> getTxnHistory(TransactionHistoryInputBean bean, String orderBy, int rows, int from, String txanctionid) throws SQLException, Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getChannelListQuery = null;
        List<TransactionHistoryBean> dataList = null;
        long totalCount = 0;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_TRANSACTION_HISTORY where TXN_ID LIKE ? ";
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

            getChannelListQuery = "SELECT TH.HISTORY_ID,TH.TXN_ID,S.DESCRIPTION AS STATUS,TH.TIMESTAMP FROM CLA_TRANSACTION_HISTORY TH"
                    + ",CLA_MT_STATUS S WHERE TH.STATUS=S.CODE AND TH.TXN_ID LIKE ?"
                    + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getChannelListQuery);
            prepSt.setString(1, "%" + txanctionid + "%");
            res = prepSt.executeQuery();

            dataList = new ArrayList<TransactionHistoryBean>();

            while (res.next()) {

                TransactionHistoryBean dataBean = new TransactionHistoryBean();
                dataBean.setId(res.getString("HISTORY_ID"));
                dataBean.setTxnid(res.getString("TXN_ID"));
                dataBean.setStatus(res.getString("STATUS"));
                dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());

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
