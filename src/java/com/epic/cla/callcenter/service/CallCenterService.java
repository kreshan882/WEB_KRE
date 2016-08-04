/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.callcenter.service;

import com.epic.cla.callcenter.bean.CallCenterBean;
import com.epic.cla.callcenter.bean.CallCenterInputBean;
import com.epic.cla.transactionmgt.bean.ViewTransactionDataBean;
import com.epic.cla.transactionmgt.bean.ViewTransactionInputBean;
import com.epic.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dimuthu_h
 */
public class CallCenterService {

    public List<CallCenterBean> loadHistory(CallCenterInputBean bean, String orderBy, int max, int first) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getHistoryRecordsQuery = null;
        List<CallCenterBean> dataList = null;
        long totalCount = 0;

        if (bean.isSearch()) {

            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);

                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_TRANSACTION   WHERE CUSTOMER_ACCOUNT_NUMBER LIKE ?  AND CUSTOMER_MOBILE LIKE ? AND RECEPIENT_MOBILE  LIKE ? AND AMOUNT LIKE ? AND REF_NO LIKE ? AND ORD_ID LIKE ?  AND TIMESTAMP BETWEEN ? AND ?";

                prepSt = con.prepareStatement(sqlCount);

                prepSt.setString(1, "%" + bean.getCustomerAccNumber() + "%");
                prepSt.setString(2, "%" + bean.getCustomerMobileNumber() + "%");
                prepSt.setString(3, "%" + bean.getRecepientMobile() + "%");
                prepSt.setString(4, "%" + bean.getAmount() + "%");
                prepSt.setString(5, "%" + bean.getReferenceNumber() + "%");
                prepSt.setString(6, "%" + bean.getOrderID() + "%");
                prepSt.setTimestamp(7, getTimeStamp(bean.getFromdate()));
                prepSt.setTimestamp(8, getTimeStamp(bean.getTodate()));
                res = prepSt.executeQuery();
                while (res.next()) {

                    totalCount = res.getLong("TOTAL");
                }

                if (res != null) {
                    res.close();
                }
                if (prepSt != null) {
                    prepSt.close();
                }

                getHistoryRecordsQuery = "SELECT T.TXN_ID AS ID,TT.DESCRIPTION AS TXN_TYPE,C.NAME AS CUSTOMER_ID,T.RECEPIENT_MOBILE AS RECEPIENT_MOBILE,T.AMOUNT AS AMOUNT,T.ORD_ID AS ORD_ID,T.CUSTOMER_ACCOUNT_NUMBER AS CUSTOMER_ACCOUNT_NUMBER,CT.DESCRIPTION AS CHANNEL_TYPE,T.CUSTOMER_MOBILE AS CUSTOMER_MOBILE,T.REF_NO AS REF_NO,T.SERVICE_CHARGE AS SERVICE_CHARGE  ,T.TXN_DATE_TIME AS TXN_DATE,RC.CODE AS RESPCODE "
                        + "FROM CLA_TRANSACTION T,CLA_MT_TXN_TYPE TT,CLA_MT_CHANNEL_TYPE CT,CLA_MT_RESPONSE_CODE RC,CLA_CUSTOMER C "
                        + "WHERE T.TXN_TYPE= TT.CODE AND T.CHANNEL_TYPE=CT.CODE AND T.RESPONSE_CODE = RC.CODE AND T.CUSTOMER_ID=C.CUSTOMER_ID AND T.CUSTOMER_ACCOUNT_NUMBER LIKE ?  AND T.CUSTOMER_MOBILE LIKE ? AND  T.RECEPIENT_MOBILE  LIKE ? AND T.AMOUNT LIKE ? AND T.REF_NO LIKE ? AND T.ORD_ID LIKE ?  AND T.TIMESTAMP BETWEEN ? AND ?" + orderBy + " LIMIT " + first + "," + max;

                prepSt = con.prepareStatement(getHistoryRecordsQuery);

                prepSt.setString(1, "%" + bean.getCustomerAccNumber() + "%");
                prepSt.setString(2, "%" + bean.getCustomerMobileNumber() + "%");
                prepSt.setString(3, "%" + bean.getRecepientMobile() + "%");
                prepSt.setString(4, "%" + bean.getAmount() + "%");
                prepSt.setString(5, "%" + bean.getReferenceNumber() + "%");
                prepSt.setString(6, "%" + bean.getOrderID() + "%");
                prepSt.setTimestamp(7, getTimeStamp(bean.getFromdate()));
                prepSt.setTimestamp(8, getTimeStamp(bean.getTodate()));

                res = prepSt.executeQuery();

                dataList = new ArrayList<CallCenterBean>();

                CallCenterBean dataBean = null;

                while (res.next()) {

                    dataBean = new CallCenterBean();

                    dataBean = new CallCenterBean();
                    dataBean.setId(res.getString("ID"));
                    dataBean.setTxn_type(res.getString("TXN_TYPE"));
                    dataBean.setCustomerID(res.getString("CUSTOMER_ID"));
                    dataBean.setRecepientMobile(res.getString("RECEPIENT_MOBILE"));
                    dataBean.setAmount(res.getString("AMOUNT"));
                    dataBean.setOrderID(res.getString("ORD_ID"));
                    dataBean.setCustomerAccountNumber(res.getString("CUSTOMER_ACCOUNT_NUMBER"));
                    dataBean.setChannel(res.getString("CHANNEL_TYPE"));
                    dataBean.setCustomerMobile(res.getString("CUSTOMER_MOBILE"));
                    dataBean.setRefNo(res.getString("REF_NO"));
                    dataBean.setServiceCharge(res.getString("SERVICE_CHARGE"));
                    dataBean.setDatetime(res.getString("TXN_DATE"));
                    dataBean.setResponseCode(res.getString("RESPCODE"));
                    dataBean.setFullCount(totalCount);
                    dataList.add(dataBean);

                }
                
            } catch (Exception e) {
                if (con != null) {
                    con.rollback();
                }
                throw e;

            } finally {
                if (res != null) {
                    res.close();
                }
                if (prepSt != null) {
                    prepSt.close();
                }
                if (con != null) {
                    con.close();
                }

            }

        } else {

            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);
                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_TRANSACTION T";
                prepSt = con.prepareStatement(sqlCount);
                res = prepSt.executeQuery();
                while (res.next()) {

                    totalCount = res.getLong("TOTAL");
                }

                if (res != null) {
                    res.close();
                }
                if (prepSt != null) {
                    prepSt.close();
                }

                getHistoryRecordsQuery = "SELECT T.TXN_ID AS ID,TT.DESCRIPTION AS TXN_TYPE,C.NAME AS CUSTOMER_ID,T.RECEPIENT_MOBILE AS RECEPIENT_MOBILE,T.AMOUNT AS AMOUNT,T.ORD_ID AS ORD_ID,T.CUSTOMER_ACCOUNT_NUMBER AS CUSTOMER_ACCOUNT_NUMBER,CT.DESCRIPTION AS CHANNEL_TYPE,T.CUSTOMER_MOBILE AS CUSTOMER_MOBILE,T.REF_NO AS REF_NO,T.SERVICE_CHARGE AS SERVICE_CHARGE  ,T.TXN_DATE_TIME AS TXN_DATE,RC.CODE AS RESPCODE "
                        + "FROM CLA_TRANSACTION T,CLA_MT_TXN_TYPE TT,CLA_MT_CHANNEL_TYPE CT,CLA_MT_RESPONSE_CODE RC,CLA_CUSTOMER C "
                        + "WHERE T.TXN_TYPE= TT.CODE AND T.CHANNEL_TYPE=CT.CODE AND T.RESPONSE_CODE = RC.CODE AND T.CUSTOMER_ID=C.CUSTOMER_ID" + orderBy + " LIMIT " + first + "," + max;
                prepSt = con.prepareStatement(getHistoryRecordsQuery);

                res = prepSt.executeQuery();
                dataList = new ArrayList<CallCenterBean>();

                CallCenterBean dataBean = null;

                while (res.next()) {

                    dataBean = new CallCenterBean();
                    dataBean.setId(res.getString("ID"));
                    dataBean.setTxn_type(res.getString("TXN_TYPE"));
                    dataBean.setCustomerID(res.getString("CUSTOMER_ID"));
                    dataBean.setRecepientMobile(res.getString("RECEPIENT_MOBILE"));
                    dataBean.setAmount(res.getString("AMOUNT"));
                    dataBean.setOrderID(res.getString("ORD_ID"));
                    dataBean.setCustomerAccountNumber(res.getString("CUSTOMER_ACCOUNT_NUMBER"));
                    dataBean.setChannel(res.getString("CHANNEL_TYPE"));
                    dataBean.setCustomerMobile(res.getString("CUSTOMER_MOBILE"));
                    dataBean.setRefNo(res.getString("REF_NO"));
                    dataBean.setServiceCharge(res.getString("SERVICE_CHARGE"));
                    dataBean.setDatetime(res.getString("TXN_DATE"));
                    dataBean.setResponseCode(res.getString("RESPCODE"));
                    dataBean.setFullCount(totalCount);
                    dataList.add(dataBean);

                }

                
            } catch (Exception e) {
                if (con != null) {
                    con.rollback();
                }
                throw e;

            } finally {
                if (res != null) {
                    res.close();
                }
                if (prepSt != null) {
                    prepSt.close();
                }
                if (con != null) {
                    con.close();
                }

            }
        }

        return dataList;
    }

    private Timestamp getTimeStamp(String date) throws Exception {

        SimpleDateFormat formtter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dtt = formtter.parse(date);

        return new java.sql.Timestamp(dtt.getTime());
    }

}
