/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.service;

import com.epic.cla.transactionmgt.bean.ViewTransactionDataBean;
import com.epic.cla.transactionmgt.bean.ViewTransactionInputBean;
import com.epic.db.DBConnection;
import com.epic.login.bean.SessionUserBean;
import com.epic.util.CommunicationChannelHandler;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tharaka
 */
public class ViewTransactionService {

    public List<ViewTransactionDataBean> loadHistory(ViewTransactionInputBean bean, String orderBy, int max, int first) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getHistoryRecordsQuery = null;
        List<ViewTransactionDataBean> dataList = null;
        long totalCount = 0;
        if (bean.isSearch()) {
            String where = " date(TIMESTAMP) BETWEEN '" + dateFormat(bean.getFromdate()) + "' AND '" + dateFormat(bean.getTodate()) + "'";

//            if (!bean.getOrderID().equals("")) {
//                where = "ORD_ID LIKE '%" + bean.getOrderID() + "%' AND " + where;
//            }
            if (!bean.getRecepientMobile().equals("")) {
                where = "RECEPIENT_MOBILE LIKE '%" + bean.getRecepientMobile() + "%' AND " + where;
            }
            if (!bean.getReferenceNumber().equals("")) {
                where = "TRACE_NO LIKE '%" + bean.getReferenceNumber() + "%' AND " + where;
            }
            if (!bean.getBatchNumber().equals("")) {
                where = "BATCH_NO LIKE '%" + bean.getBatchNumber() + "%' AND " + where;
            }
            if (!bean.getCustomerMobileNumber().equals("")) {
                where = "CUSTOMER_MOBILE LIKE '%" + bean.getCustomerMobileNumber() + "%' AND " + where;
            }
            if (!bean.getCustomerAccNumber().equals("")) {
                where = "CUSTOMER_ACCOUNT_NUMBER LIKE '%" + bean.getCustomerAccNumber() + "%' AND " + where;
            }
            if (!bean.getAmount().equals("")) {
                where = "AMOUNT = " + bean.getAmount() + " AND " + where;
            }
            if (!bean.getChanneltype().equals("-1")) {
                where = "CHANNEL_TYPE LIKE '%" + bean.getChanneltype() + "%' AND " + where;
            }
            if (!bean.getTxntype().equals("-1")) {
                where = "STATUS = " + bean.getTxntype() + " AND " + where;
            }
            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);

                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_TRANSACTION   WHERE " + where;

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

                getHistoryRecordsQuery = "SELECT  * FROM (select t.TXN_ID AS ID,tt.DESCRIPTION as TXN_TYPE, T.CUSTOMER_ID,\n"
                        + "T.RECEPIENT_MOBILE AS RECEPIENT_MOBILE,\n"
                        + "t.CHANNEL_TYPE,T.AMOUNT AS AMOUNT,T.ORD_ID AS ORD_ID,T.CUSTOMER_ACCOUNT_NUMBER AS CUSTOMER_ACCOUNT_NUMBER,\n"
                        + "T.CUSTOMER_MOBILE AS CUSTOMER_MOBILE,T.TRACE_NO AS REF_NO,T.BATCH_NO,T.SERVICE_CHARGE AS SERVICE_CHARGE  ,\n"
                        + "T.TXN_DATE_TIME AS TXN_DATE,T.TIMESTAMP,T.RESPONSE_CODE,S.DESCRIPTION AS STATUS,T.STATUS AS STATUS_CODE,"
                        + "T.COLLECTION_ACCOUNT,T.GL_ACCOUNT,T.COST_CENTER,T.ITM_TID,T.ITM_LOCATION \n"
                        + "from CLA_TRANSACTION t,CLA_MT_TXN_TYPE tt,\n"
                        + "CLA_MT_STATUS S \n"
                        + "where " + where + " and tt.CODE = t.TXN_TYPE AND T.STATUS=S.CODE)t\n"
                        + "LEFT OUTER JOIN (select DESCRIPTION AS CH_TYPE ,CODE FROM CLA_MT_LISTENER_TYPE)  t2\n"
                        + "ON t.CHANNEL_TYPE = t2.CODE \n"
                        + "LEFT  OUTER JOIN (select name AS CUSTOMER_NAME,CUSTOMER_ID FROM CLA_CUSTOMER)  t3\n"
                        + "ON t.CUSTOMER_ID = t3.CUSTOMER_ID\n"
                        + "LEFT OUTER JOIN (select description AS RES_DES ,code FROM CLA_MT_RESPONSE_CODE)  t4\n"
                        + "ON t.RESPONSE_CODE = t4.code" + orderBy + " LIMIT " + first + "," + max;

                prepSt = con.prepareStatement(getHistoryRecordsQuery);
                res = prepSt.executeQuery();

                dataList = new ArrayList<ViewTransactionDataBean>();

                ViewTransactionDataBean dataBean = null;

                while (res.next()) {

                    dataBean = new ViewTransactionDataBean();

                    dataBean = new ViewTransactionDataBean();
                    dataBean.setId(res.getString("ID"));
                    dataBean.setTxn_type(res.getString("TXN_TYPE"));
                    dataBean.setCustomerID(res.getString("CUSTOMER_NAME"));
                    dataBean.setRecepientMobile(res.getString("RECEPIENT_MOBILE"));
                    dataBean.setAmount(Util.round(res.getString("AMOUNT")));
                    dataBean.setOrderID(res.getString("ORD_ID"));
                    dataBean.setCustomerAccountNumber(res.getString("CUSTOMER_ACCOUNT_NUMBER"));
                    dataBean.setChannel(res.getString("CH_TYPE"));
                    dataBean.setCustomerMobile(res.getString("CUSTOMER_MOBILE"));
                    dataBean.setRefNo(res.getString("REF_NO"));
                    dataBean.setBatchNumber(res.getString("BATCH_NO"));
                    dataBean.setServiceCharge(Util.round(res.getString("SERVICE_CHARGE")));
                    dataBean.setCollectionaccount(res.getString("COLLECTION_ACCOUNT"));
                    dataBean.setGlaccount(res.getString("GL_ACCOUNT"));
                    dataBean.setCostcenter(res.getString("COST_CENTER"));
                    dataBean.setItmtid(res.getString("ITM_TID"));
                    dataBean.setItmlocation(res.getString("ITM_LOCATION"));
                    dataBean.setDatetime(res.getString("TXN_DATE"));
                    dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());
                    dataBean.setStatus(statusTypeList().get(Integer.parseInt(res.getString("STATUS_CODE"))));
                    dataBean.setStatusCode(res.getString("STATUS_CODE"));
                    dataBean.setResponseCode(res.getString("RES_DES"));
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

                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_TRANSACTION where TXN_TYPE=3";
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

                getHistoryRecordsQuery = "select * from (select t.TXN_ID AS ID,tt.DESCRIPTION as TXN_TYPE,t.CUSTOMER_ID,T.RECEPIENT_MOBILE AS RECEPIENT_MOBILE,\n"
                        + "t.CHANNEL_TYPE,T.AMOUNT AS AMOUNT,T.ORD_ID AS ORD_ID,T.CUSTOMER_ACCOUNT_NUMBER AS CUSTOMER_ACCOUNT_NUMBER,\n"
                        + "T.CUSTOMER_MOBILE AS CUSTOMER_MOBILE,T.TRACE_NO AS REF_NO,T.BATCH_NO,T.SERVICE_CHARGE AS SERVICE_CHARGE  ,\n"
                        + "T.TXN_DATE_TIME AS TXN_DATE,T.TIMESTAMP,T.RESPONSE_CODE,S.DESCRIPTION AS STATUS,T.STATUS AS STATUS_CODE,T.COLLECTION_ACCOUNT,T.GL_ACCOUNT,T.COST_CENTER,T.ITM_TID,T.ITM_LOCATION \n"
                        + "from CLA_TRANSACTION t,CLA_MT_TXN_TYPE tt,\n"
                        + "CLA_MT_STATUS S \n"
                        + "where tt.CODE = t.TXN_TYPE and T.STATUS=S.CODE and T.TXN_TYPE=3) t\n"
                        + "LEFT OUTER JOIN (select DESCRIPTION AS CH_TYPE ,CODE FROM CLA_MT_LISTENER_TYPE)  t2\n"
                        + "ON t.CHANNEL_TYPE = t2.CODE\n"
                        + "LEFT OUTER JOIN (select name AS CUSTOMER_NAME ,customer_id FROM CLA_CUSTOMER)  t3\n"
                        + "ON t.customer_id = t3.customer_id\n"
                        + "LEFT OUTER JOIN (select description AS RES_DES ,code FROM CLA_MT_RESPONSE_CODE)  t4\n"
                        + "ON t.RESPONSE_CODE = t4.code" + orderBy + " LIMIT " + first + "," + max;
                prepSt = con.prepareStatement(getHistoryRecordsQuery);
                res = prepSt.executeQuery();
                dataList = new ArrayList<ViewTransactionDataBean>();

                ViewTransactionDataBean dataBean = null;

                while (res.next()) {
                    dataBean = new ViewTransactionDataBean();
                    dataBean.setId(res.getString("ID"));
                    dataBean.setTxn_type(res.getString("TXN_TYPE"));
                    dataBean.setCustomerID(res.getString("CUSTOMER_NAME"));
                    dataBean.setRecepientMobile(res.getString("RECEPIENT_MOBILE"));
                    dataBean.setAmount(Util.round(res.getString("AMOUNT")));
                    dataBean.setOrderID(res.getString("ORD_ID"));
                    dataBean.setCustomerAccountNumber(res.getString("CUSTOMER_ACCOUNT_NUMBER"));
                    dataBean.setChannel(res.getString("CH_TYPE"));
                    dataBean.setCustomerMobile(res.getString("CUSTOMER_MOBILE"));
                    dataBean.setRefNo(res.getString("REF_NO"));
                    dataBean.setBatchNumber(res.getString("BATCH_NO"));
                    dataBean.setServiceCharge(Util.round(res.getString("SERVICE_CHARGE")));
                    dataBean.setCollectionaccount(res.getString("COLLECTION_ACCOUNT"));
                    dataBean.setGlaccount(res.getString("GL_ACCOUNT"));
                    dataBean.setCostcenter(res.getString("COST_CENTER"));
                    dataBean.setItmtid(res.getString("ITM_TID"));
                    dataBean.setItmlocation(res.getString("ITM_LOCATION"));
                    dataBean.setDatetime(res.getString("TXN_DATE"));
                    dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());
                    dataBean.setStatus(statusTypeList().get(Integer.parseInt(res.getString("STATUS_CODE"))));
                    dataBean.setStatusCode(res.getString("STATUS_CODE"));
                    dataBean.setResponseCode(res.getString("RES_DES"));
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

    public static Map<Integer, String> getChannelTypeList() throws Exception {
        Map<Integer, String> channelTypeList = new HashMap<Integer, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT CODE,DESCRIPTION FROM CLA_MT_LISTENER_TYPE WHERE LISTNER_CATEGORY='2'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                channelTypeList.put(rs.getInt("CODE"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }

        }

        return channelTypeList;
    }

    private Timestamp getTimeStamp(String date) throws Exception {

        SimpleDateFormat formtter = new SimpleDateFormat("dd/MM/yy");
        java.util.Date dtt = formtter.parse(date);

        return new java.sql.Timestamp(dtt.getTime());
    }

    public List<ViewTransactionDataBean> downloadData(ViewTransactionInputBean bean) throws SQLException, Exception {
        //System.out.println("here 4");
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getHistoryRecordsQuery = null;
        List<ViewTransactionDataBean> dataList = null;
        long totalCount = 0;
        String where = " date(TIMESTAMP) BETWEEN '" + dateFormat(bean.getFromdate()) + "' AND '" + dateFormat(bean.getTodate()) + "'";

//        if (!bean.getOrderID().equals("")) {
//            where = "ORD_ID LIKE '%" + bean.getOrderID() + "%' AND " + where;
//        }
        if (!bean.getRecepientMobile().equals("")) {
            where = "RECEPIENT_MOBILE LIKE '%" + bean.getRecepientMobile() + "%' AND " + where;
        }
        if (!bean.getReferenceNumber().equals("")) {
            where = "TRACE_NO LIKE '%" + bean.getReferenceNumber() + "%' AND " + where;
        }
        if (!bean.getBatchNumber().equals("")) {
            where = "BATCH_NO LIKE '%" + bean.getBatchNumber() + "%' AND " + where;
        }
        if (!bean.getCustomerMobileNumber().equals("")) {
            where = "CUSTOMER_MOBILE LIKE '%" + bean.getCustomerMobileNumber() + "%' AND " + where;
        }
        if (!bean.getCustomerAccNumber().equals("")) {
            where = "CUSTOMER_ACCOUNT_NUMBER LIKE '%" + bean.getCustomerAccNumber() + "%' AND " + where;
        }
        if (!bean.getAmount().equals("")) {
            where = "AMOUNT = " + bean.getAmount() + " AND " + where;
        }
        if (!bean.getChanneltype().equals("-1")) {
            where = "CHANNEL_TYPE LIKE '%" + bean.getChanneltype() + "%' AND " + where;
        }
        if (!bean.getTxntype().equals("-1")) {
            where = "STATUS = " + bean.getTxntype() + " AND " + where;
        }
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            getHistoryRecordsQuery = "SELECT  * FROM (select t.TXN_ID AS ID,tt.DESCRIPTION as TXN_TYPE, T.CUSTOMER_ID,\n"
                    + "T.RECEPIENT_MOBILE AS RECEPIENT_MOBILE,\n"
                    + "t.CHANNEL_TYPE,T.AMOUNT AS AMOUNT,T.ORD_ID AS ORD_ID,T.CUSTOMER_ACCOUNT_NUMBER AS CUSTOMER_ACCOUNT_NUMBER,\n"
                    + "T.CUSTOMER_MOBILE AS CUSTOMER_MOBILE,T.TRACE_NO AS REF_NO,T.BATCH_NO,T.SERVICE_CHARGE AS SERVICE_CHARGE  ,\n"
                    + "T.TXN_DATE_TIME AS TXN_DATE,T.TIMESTAMP,T.RESPONSE_CODE,S.DESCRIPTION AS STATUS\n"
                    + "from CLA_TRANSACTION t,CLA_MT_TXN_TYPE tt,\n"
                    + "CLA_MT_STATUS S \n"
                    + "where " + where + " and tt.CODE = t.TXN_TYPE AND T.STATUS=S.CODE)t\n"
                    + "LEFT OUTER JOIN (select DESCRIPTION AS CH_TYPE ,CODE FROM CLA_MT_LISTENER_TYPE)  t2\n"
                    + "ON t.CHANNEL_TYPE = t2.CODE \n"
                    + "LEFT  OUTER JOIN (select name AS CUSTOMER_NAME,CUSTOMER_ID FROM CLA_CUSTOMER)  t3\n"
                    + "ON t.CUSTOMER_ID = t3.CUSTOMER_ID";

            prepSt = con.prepareStatement(getHistoryRecordsQuery);

            res = prepSt.executeQuery();

            dataList = new ArrayList<ViewTransactionDataBean>();

            ViewTransactionDataBean dataBean = null;

            while (res.next()) {

                dataBean = new ViewTransactionDataBean();
                dataBean.setTXN_TYPE(res.getString("TXN_TYPE"));
                dataBean.setCUS_NAME(res.getString("CUSTOMER_NAME"));
                dataBean.setREC_MOBILE(res.getString("RECEPIENT_MOBILE"));
                dataBean.setAMOUNT("Rs " + Util.round(res.getString("AMOUNT")));
                dataBean.setORDER_ID(res.getString("ORD_ID"));
                dataBean.setCUS_ACOUNT(res.getString("CUSTOMER_ACCOUNT_NUMBER"));
                dataBean.setChannel(res.getString("CH_TYPE"));
                dataBean.setCUS_MOBILE(res.getString("CUSTOMER_MOBILE"));
                dataBean.setSERVICE_FEE(Util.round(res.getString("SERVICE_CHARGE")));
                dataBean.setDATETIME(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());
                dataBean.setFullCount(totalCount);
                dataList.add(dataBean);

            }

        } catch (Exception e) {
            e.printStackTrace();
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

        return dataList;

    }

    public boolean CancelTransaction(ViewTransactionInputBean inputBean) throws SQLException, Exception {
        boolean ok = false;
        try {

            String req = "4|25|" + inputBean.getId();
            String res = CommunicationChannelHandler.ReqAndResponse(req);
            ok = true;

        } catch (Exception e) {
            throw e;
        }
        return ok;
    }

    public List<ViewTransactionDataBean> loadPrivateHistory(ViewTransactionInputBean bean, String orderBy, int max, int first, SessionUserBean sub) throws SQLException, Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getHistoryRecordsQuery = null;
        List<ViewTransactionDataBean> dataList = null;
        long totalCount = 0;

        if (bean.isSearch()) {
            String where = " date(TIMESTAMP) BETWEEN '" + dateFormat(bean.getFromdate()) + "' AND '" + dateFormat(bean.getTodate()) + "'";

            if (!bean.getOrderID().equals("")) {
                where = "ORD_ID LIKE '%" + bean.getOrderID() + "%' AND " + where;
            }
            if (!bean.getRecepientMobile().equals("")) {
                where = "RECEPIENT_MOBILE LIKE '%" + bean.getRecepientMobile() + "%' AND " + where;
            }
            if (!bean.getReferenceNumber().equals("")) {
                where = "TRACE_NO LIKE '%" + bean.getReferenceNumber() + "%' AND " + where;
            }
            if (!bean.getBatchNumber().equals("")) {
                where = "BATCH_NO LIKE '%" + bean.getBatchNumber() + "%' AND " + where;
            }
            if (!bean.getCustomerMobileNumber().equals("")) {
                where = "CUSTOMER_MOBILE LIKE '%" + bean.getCustomerMobileNumber() + "%' AND " + where;
            }
            if (!bean.getCustomerAccNumber().equals("")) {
                where = "CUSTOMER_ACCOUNT_NUMBER LIKE '%" + bean.getCustomerAccNumber() + "%' AND " + where;
            }
            if (!bean.getAmount().equals("")) {
                where = "AMOUNT = " + bean.getAmount() + " AND " + where;
            }
            if (!bean.getChanneltype().equals("-1")) {
                where = "CHANNEL_TYPE LIKE '%" + bean.getChanneltype() + "%' AND " + where;
            }
            if (!bean.getTxntype().equals("-1")) {
                where = "STATUS = " + bean.getTxntype() + " AND " + where;
            }
            try {
                con = DBConnection.getConnection();
                //con.setAutoCommit(true);

                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_TRANSACTION   WHERE " + where + " AND CUSTOMER_ID=?";

                prepSt = con.prepareStatement(sqlCount);

                prepSt.setInt(1, sub.getCusId());
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

                getHistoryRecordsQuery = "SELECT  * FROM (select t.TXN_ID AS ID,tt.DESCRIPTION as TXN_TYPE, T.CUSTOMER_ID,\n"
                        + "T.RECEPIENT_MOBILE AS RECEPIENT_MOBILE,\n"
                        + "t.CHANNEL_TYPE,T.AMOUNT AS AMOUNT,T.ORD_ID AS ORD_ID,T.CUSTOMER_ACCOUNT_NUMBER AS CUSTOMER_ACCOUNT_NUMBER,\n"
                        + "T.CUSTOMER_MOBILE AS CUSTOMER_MOBILE,T.TRACE_NO AS REF_NO,T.BATCH_NO,T.SERVICE_CHARGE AS SERVICE_CHARGE  ,\n"
                        + "T.TXN_DATE_TIME AS TXN_DATE,T.TIMESTAMP,T.RESPONSE_CODE,S.DESCRIPTION AS STATUS,T.STATUS AS STATUS_CODE,"
                        + "T.COLLECTION_ACCOUNT,T.GL_ACCOUNT,T.COST_CENTER,T.ITM_TID,T.ITM_LOCATION \n"
                        + "from CLA_TRANSACTION t,CLA_MT_TXN_TYPE tt,\n"
                        + "CLA_MT_STATUS S \n"
                        + "where " + where + " AND tt.CODE = t.TXN_TYPE AND T.STATUS=S.CODE and  CUSTOMER_ID=?)t\n"
                        + "LEFT OUTER JOIN (select DESCRIPTION AS CH_TYPE ,CODE FROM CLA_MT_LISTENER_TYPE)  t2\n"
                        + "ON t.CHANNEL_TYPE = t2.CODE \n"
                        + "LEFT  OUTER JOIN (select name AS CUSTOMER_NAME,CUSTOMER_ID FROM CLA_CUSTOMER)  t3\n"
                        + "ON t.CUSTOMER_ID = t3.CUSTOMER_ID\n"
                        + "LEFT OUTER JOIN (select description AS RES_DES ,code FROM CLA_MT_RESPONSE_CODE)  t4\n"
                        + "ON t.RESPONSE_CODE = t4.code" + orderBy + " LIMIT " + first + "," + max;

                prepSt = con.prepareStatement(getHistoryRecordsQuery);

                prepSt.setInt(1, sub.getCusId());
                res = prepSt.executeQuery();

                dataList = new ArrayList<ViewTransactionDataBean>();

                ViewTransactionDataBean dataBean = null;

                while (res.next()) {

                    dataBean = new ViewTransactionDataBean();

                    dataBean = new ViewTransactionDataBean();
                    dataBean.setId(res.getString("ID"));
                    dataBean.setTxn_type(res.getString("TXN_TYPE"));
                    dataBean.setCustomerID(res.getString("CUSTOMER_ID"));
                    dataBean.setRecepientMobile(res.getString("RECEPIENT_MOBILE"));
                    dataBean.setAmount(Util.round(res.getString("AMOUNT")));
                    dataBean.setOrderID(res.getString("ORD_ID"));
                    dataBean.setCustomerAccountNumber(res.getString("CUSTOMER_ACCOUNT_NUMBER"));
                    dataBean.setChannel(res.getString("CH_TYPE"));
                    dataBean.setCustomerMobile(res.getString("CUSTOMER_MOBILE"));
                    dataBean.setRefNo(res.getString("REF_NO"));
                    dataBean.setBatchNumber(res.getString("BATCH_NO"));
                    dataBean.setServiceCharge(Util.round(res.getString("SERVICE_CHARGE")));
                    dataBean.setCollectionaccount(res.getString("COLLECTION_ACCOUNT"));
                    dataBean.setGlaccount(res.getString("GL_ACCOUNT"));
                    dataBean.setCostcenter(res.getString("COST_CENTER"));
                    dataBean.setItmtid(res.getString("ITM_TID"));
                    dataBean.setItmlocation(res.getString("ITM_LOCATION"));
                    dataBean.setDatetime(res.getString("TXN_DATE"));
                    dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());
                    dataBean.setStatus(statusTypeList().get(Integer.parseInt(res.getString("STATUS_CODE"))));
                    dataBean.setStatusCode(res.getString("STATUS_CODE"));
                    dataBean.setResponseCode(res.getString("RES_DES"));
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

                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_TRANSACTION WHERE CUSTOMER_ID=?";
                prepSt = con.prepareStatement(sqlCount);
                prepSt.setInt(1, sub.getCusId());
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

                getHistoryRecordsQuery = "select * from (select t.TXN_ID AS ID,tt.DESCRIPTION as TXN_TYPE, c.NAME as CUSTOMER_ID,T.RECEPIENT_MOBILE AS RECEPIENT_MOBILE,\n"
                        + "t.CHANNEL_TYPE,T.AMOUNT AS AMOUNT,T.ORD_ID AS ORD_ID,T.CUSTOMER_ACCOUNT_NUMBER AS CUSTOMER_ACCOUNT_NUMBER,\n"
                        + "T.CUSTOMER_MOBILE AS CUSTOMER_MOBILE,T.TRACE_NO AS REF_NO,T.BATCH_NO,T.SERVICE_CHARGE AS SERVICE_CHARGE  ,\n"
                        + "T.TXN_DATE_TIME AS TXN_DATE,T.TIMESTAMP,T.RESPONSE_CODE,S.DESCRIPTION AS STATUS,T.STATUS AS STATUS_CODE,"
                        + "T.COLLECTION_ACCOUNT,T.GL_ACCOUNT,T.COST_CENTER,T.ITM_TID,T.ITM_LOCATION \n"
                        + "from CLA_TRANSACTION t,CLA_CUSTOMER c,CLA_MT_TXN_TYPE tt,\n"
                        + "CLA_MT_STATUS S \n"
                        + "where tt.CODE = t.TXN_TYPE and c.CUSTOMER_ID = t.CUSTOMER_ID AND T.STATUS=S.CODE AND t.CUSTOMER_ID=?) t\n"
                        + "LEFT OUTER JOIN (select DESCRIPTION AS CH_TYPE ,CODE FROM CLA_MT_LISTENER_TYPE)  t2\n"
                        + "ON t.CHANNEL_TYPE = t2.CODE \n"
                        + "LEFT OUTER JOIN (select description AS RES_DES ,code FROM CLA_MT_RESPONSE_CODE)  t4\n"
                        + "ON t.RESPONSE_CODE = t4.code" + orderBy + " LIMIT " + first + "," + max;
                prepSt = con.prepareStatement(getHistoryRecordsQuery);
                prepSt.setInt(1, sub.getCusId());
                res = prepSt.executeQuery();
                dataList = new ArrayList<ViewTransactionDataBean>();

                ViewTransactionDataBean dataBean = null;

                while (res.next()) {
                    dataBean = new ViewTransactionDataBean();
                    dataBean.setId(res.getString("ID"));
                    dataBean.setTxn_type(res.getString("TXN_TYPE"));
                    dataBean.setCustomerID(res.getString("CUSTOMER_ID"));
                    dataBean.setRecepientMobile(res.getString("RECEPIENT_MOBILE"));
                    dataBean.setAmount(Util.round(res.getString("AMOUNT")));
                    dataBean.setOrderID(res.getString("ORD_ID"));
                    dataBean.setCustomerAccountNumber(res.getString("CUSTOMER_ACCOUNT_NUMBER"));
                    dataBean.setChannel(res.getString("CH_TYPE"));
                    dataBean.setCustomerMobile(res.getString("CUSTOMER_MOBILE"));
                    dataBean.setRefNo(res.getString("REF_NO"));
                    dataBean.setBatchNumber(res.getString("BATCH_NO"));
                    dataBean.setServiceCharge(Util.round(res.getString("SERVICE_CHARGE")));
                    dataBean.setCollectionaccount(res.getString("COLLECTION_ACCOUNT"));
                    dataBean.setGlaccount(res.getString("GL_ACCOUNT"));
                    dataBean.setCostcenter(res.getString("COST_CENTER"));
                    dataBean.setItmtid(res.getString("ITM_TID"));
                    dataBean.setItmlocation(res.getString("ITM_LOCATION"));
                    dataBean.setDatetime(res.getString("TXN_DATE"));
                    dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());
                    dataBean.setStatus(statusTypeList().get(Integer.parseInt(res.getString("STATUS_CODE"))));
                    dataBean.setStatusCode(res.getString("STATUS_CODE"));
                    dataBean.setResponseCode(res.getString("RES_DES"));
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

    public static String dateFormat(String date) throws ParseException {
        DateFormat original = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat target = new SimpleDateFormat("dd/MM/yy");
        Date datea = original.parse(date);
        String formattedDate = target.format(datea);

        return formattedDate;
    }

    public Map<Integer, String> statusTypeList() throws Exception {
        Map<Integer, String> statusTypeList = new HashMap<Integer, String>();
        statusTypeList.put(6, "Customer Request");
        statusTypeList.put(25, "Manual Reversed");
        statusTypeList.put(24, "Auto Reversed");
        statusTypeList.put(10, "Completed");
        statusTypeList.put(26, "ITM Reversed");
        return statusTypeList;
    }
}
