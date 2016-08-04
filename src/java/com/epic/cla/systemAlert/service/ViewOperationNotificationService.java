/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.service;

import com.epic.cla.systemAlert.bean.ViewOperationNotificationBean;
import com.epic.cla.systemAlert.bean.ViewOperationNotificationInputBean;
import com.epic.db.DBConnection;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dimuthu_h
 */
public class ViewOperationNotificationService {

//    public void getProfileList(ViewOperationNotificationInputBean inputBean) throws Exception {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet result = null;
//
//        try {
//            connection = DBConnection.getConnection();
//            connection.setAutoCommit(true);
//            String sql = " SELECT PROFILE_ID,DESCRIPTION FROM CLACA_SCHEMA.CLA_USER_PROFILE where STATUS=?";
//            ps = connection.prepareStatement(sql);
//            ps.setInt(1, Status.ACTIVE);
//            result = ps.executeQuery();
//
//            while (result.next()) {
//                inputBean.getUserProList().put(result.getString("PROFILE_ID"), result.getString("DESCRIPTION"));
//            }
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            if (ps != null) {
//                ps.close();
//            }
//            if (result != null) {
//                result.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
//    public List<ViewOperationNotificationBean> loadData(ViewOperationNotificationInputBean bean, String orderBy, int from, int rows) throws Exception {
//        System.out.println("loadData");
//        PreparedStatement prepSt = null;
//        ResultSet res = null;
//        Connection con = null;
//        String getUsersListQuery = null;
//        List<ViewOperationNotificationBean> dataList = null;
//        long totalCount = 0;
//        if (bean.getFromdate().equals("") && bean.getTodate().equals("")) {
//            try {
//
//                con = DBConnection.getConnection();
//                //con.setAutoCommit(true);
//                String sqlCount = "select count(*) AS TOTAL FROM CLA_SYSTEM_ALERT";
//                prepSt = con.prepareStatement(sqlCount);
//
//                res = prepSt.executeQuery();
//                if (res.next()) {
//                    totalCount = res.getLong("TOTAL");
//                }
//
//                if (prepSt != null) {
//                    prepSt.close();
//                }
//                if (res != null) {
//                    res.close();
//                }
//
//                getUsersListQuery = "SELECT * FROM CLA_SYSTEM_ALERT" + orderBy + " LIMIT " + from + "," + rows;
//
//                prepSt = con.prepareStatement(getUsersListQuery);
//
//                res = prepSt.executeQuery();
//
//                dataList = new ArrayList<ViewOperationNotificationBean>();
//
//                while (res.next()) {
//
//                    ViewOperationNotificationBean dataBean = new ViewOperationNotificationBean();
//                    dataBean.setId(Integer.toString(res.getInt("ID")));
//                    dataBean.setRisklevel(Util.getRiskLevelList().get(Integer.toString(res.getInt("RISK_LEVEL"))));
//                    dataBean.setDescription(res.getString("DESCRIPTION"));
//                    dataBean.setReadstatus(Util.getReadStatusList().get(Integer.toString(res.getInt("READ_STATUS"))));
//                    dataBean.setTimestamp(res.getTimestamp("TIMESTAMP").toString());
//
//                    dataBean.setFullCount(totalCount);
//                    dataList.add(dataBean);
//                }
//            } catch (Exception e) {
//                throw e;
//            } finally {
//                if (prepSt != null) {
//                    prepSt.close();
//                }
//                if (res != null) {
//                    res.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//
//            }
//            return dataList;
//        } else {
//            try {
//
//                con = DBConnection.getConnection();
//                //con.setAutoCommit(true);
//                String sqlCount = "select count(*) AS TOTAL FROM CLA_SYSTEM_ALERT where date(TIMESTAMP) BETWEEN ? AND ?";
//                prepSt = con.prepareStatement(sqlCount);
//                String string = bean.getFromdate();
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                Date date = format.parse(string);
//
//                String string2 = bean.getTodate();
//                DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
//                Date date2 = format2.parse(string2);
//
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(date);
//                cal.set(Calendar.MILLISECOND, 0);
//                System.out.println(new java.sql.Timestamp(date.getTime()));
//                System.out.println(new java.sql.Timestamp(cal.getTimeInMillis()));
//
//                Calendar cal2 = Calendar.getInstance();
//                cal2.setTime(date2);
//                cal2.set(Calendar.MILLISECOND, 0);
//                System.out.println(new java.sql.Timestamp(date.getTime()));
//                System.out.println(new java.sql.Timestamp(cal.getTimeInMillis()));
//
//                prepSt.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
//                prepSt.setTimestamp(2, new java.sql.Timestamp(date2.getTime()));
//
//                res = prepSt.executeQuery();
//                if (res.next()) {
//                    totalCount = res.getLong("TOTAL");
//                }
//
//                if (prepSt != null) {
//                    prepSt.close();
//                }
//                if (res != null) {
//                    res.close();
//                }
//
//                getUsersListQuery = "SELECT * FROM CLA_SYSTEM_ALERT WHERE date(TIMESTAMP) BETWEEN ? AND ?" + orderBy + " LIMIT " + from + "," + rows;
//
//                prepSt = con.prepareStatement(getUsersListQuery);
//
//                prepSt.setTimestamp(1, new java.sql.Timestamp(date.getTime()));
//                prepSt.setTimestamp(2, new java.sql.Timestamp(date2.getTime()));
//                System.out.println(getUsersListQuery);
//                res = prepSt.executeQuery();
//
//                dataList = new ArrayList<ViewOperationNotificationBean>();
//
//                while (res.next()) {
//
//                    ViewOperationNotificationBean dataBean = new ViewOperationNotificationBean();
//                    dataBean.setId(Integer.toString(res.getInt("ID")));
//                    dataBean.setRisklevel(Util.getRiskLevelList().get(Integer.toString(res.getInt("RISK_LEVEL"))));
//                    dataBean.setDescription(res.getString("DESCRIPTION"));
//                    dataBean.setReadstatus(Util.getReadStatusList().get(Integer.toString(res.getInt("READ_STATUS"))));
//                    dataBean.setTimestamp(res.getTimestamp("TIMESTAMP").toString());
//
//                    dataBean.setFullCount(totalCount);
//                    dataList.add(dataBean);
//                }
//            } catch (Exception e) {
//                throw e;
//            } finally {
//                if (prepSt != null) {
//                    prepSt.close();
//                }
//                if (res != null) {
//                    res.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//
//            }
//            return dataList;
//        }
//
//    }
    public List<ViewOperationNotificationBean> loadData(ViewOperationNotificationInputBean bean, String orderBy, int from, int rows) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getChannelListQuery = null;
        List<ViewOperationNotificationBean> dataList = null;
        long totalCount = 0;

        if (!(bean.getTodate().equals("") && bean.getFromdate().equals(""))) {

            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);
                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_OPERATION_ALERT WHERE OPERATION_CODE LIKE ? AND  date(TIME_STAMP) BETWEEN ? AND ?";
                prepSt = con.prepareStatement(sqlCount);

                String string = bean.getFromdate();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(string);

                String string2 = bean.getTodate();
                DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                Date date2 = format2.parse(string2);

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(Calendar.MILLISECOND, 0);

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(date2);
                cal2.set(Calendar.MILLISECOND, 0);
                prepSt.setString(1, "%" + bean.getOperation() + "%");
                prepSt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
                prepSt.setTimestamp(3, new java.sql.Timestamp(date2.getTime()));

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

                getChannelListQuery = "SELECT OP.DESCRIPTION AS OPERATION_CODE, OA.USERNAME,OA.IP,S.DESCRIPTION AS STATUS,OA.READ_STATUS,OA.DESCRIPTION,OA.TIME_STAMP"
                        + " FROM CLA_MT_OPERATION AS OP, CLA_OPERATION_ALERT AS OA,CLA_MT_STATUS AS S"
                        + " WHERE OP.CODE = OA.OPERATION_CODE AND S.CODE = OA.STATUS AND OA.OPERATION_CODE LIKE ?  AND date(OA.TIME_STAMP) BETWEEN ? AND ? "
                        + orderBy + " LIMIT " + from + "," + rows;

                prepSt = con.prepareStatement(getChannelListQuery);


                prepSt.setString(1, "%" + bean.getOperation() + "%");
                prepSt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
                prepSt.setTimestamp(3, new java.sql.Timestamp(date2.getTime()));

                res = prepSt.executeQuery();

                dataList = new ArrayList<ViewOperationNotificationBean>();

                while (res.next()) {

                    ViewOperationNotificationBean dataBean = new ViewOperationNotificationBean();
                    dataBean.setOperationcode(res.getString("OPERATION_CODE"));
                    dataBean.setUsername(res.getString("USERNAME"));
                    dataBean.setIp(res.getString("IP"));
                    dataBean.setStatus(res.getString("STATUS"));
                    dataBean.setReadstatus(Util.getReadStatusList().get(Integer.toString(res.getInt("READ_STATUS"))));
                    dataBean.setDescription(res.getString("DESCRIPTION"));
                    dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIME_STAMP")));

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
        } else {
            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);
                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_OPERATION_ALERT";
                prepSt = con.prepareStatement(sqlCount);
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

                getChannelListQuery = "SELECT OP.DESCRIPTION AS OPERATION_CODE, OA.USERNAME,OA.IP,S.DESCRIPTION AS STATUS,OA.READ_STATUS,OA.DESCRIPTION,OA.TIME_STAMP"
                        + " FROM CLA_MT_OPERATION AS OP, CLA_OPERATION_ALERT AS OA,CLA_MT_STATUS AS S"
                        + " WHERE OP.CODE = OA.OPERATION_CODE AND S.CODE = OA.STATUS"
                        + orderBy + " LIMIT " + from + "," + rows;

                prepSt = con.prepareStatement(getChannelListQuery);
                
                res = prepSt.executeQuery();
                dataList = new ArrayList<ViewOperationNotificationBean>();
                while (res.next()) {

                    ViewOperationNotificationBean dataBean = new ViewOperationNotificationBean();
                    dataBean.setOperationcode(res.getString("OPERATION_CODE"));
                    dataBean.setUsername(res.getString("USERNAME"));
                    dataBean.setIp(res.getString("IP"));
                    dataBean.setStatus(res.getString("STATUS"));
                    dataBean.setReadstatus(Util.getReadStatusList().get(Integer.toString(res.getInt("READ_STATUS"))));
                    dataBean.setDescription(res.getString("DESCRIPTION"));
                    dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIME_STAMP")));

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

    public static HashMap<String, String> getOperationList() throws Exception {
        HashMap<String, String> operationList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT CODE,DESCRIPTION FROM CLA_MT_OPERATION";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                operationList.put(Integer.toString(rs.getInt("CODE")), rs.getString("DESCRIPTION"));
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
        return operationList;
    }

}
